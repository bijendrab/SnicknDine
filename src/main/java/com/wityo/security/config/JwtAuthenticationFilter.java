package com.wityo.security.config;

import java.io.IOException;
import java.util.Collections;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.google.gson.Gson;
import com.wityo.common.Constant;
import com.wityo.common.exception.FilterErrorResponse;
import com.wityo.modules.user.model.User;
import com.wityo.security.service.CustomUserDetailsService;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private JwtTokenProvider tokenProvider;
	
	@Autowired
	private CustomUserDetailsService customUserDetailService;
	
	Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);
	
	/*--------Method to process JWT from the HTTP Request-------------*/
	private String getJwtFromRequest(HttpServletRequest request) {
		String bearerToken = request.getHeader(Constant.HEADER_TOKEN_KEY);
		if(StringUtils.hasText(bearerToken) && bearerToken.startsWith(Constant.TOKEN_PREFIX)) {
			return bearerToken.substring(7, bearerToken.length());
		}
		return null;
	}

	/* |||||||||||||||||||COMMON FILTER FOR APPLICATION|||||||||||||||||||||| */
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {
			/*
			 * 1. Get token from request
			 * 2. Check if it is valid
			 * 3. Get Id from token
			 * 4. Authenticate the user
			 */

			/* @Description This f-t-v header will only be present for 2 cases.
			 * 1. When the user is being validated, if present jwt will be returned
			 * 2. User is not present in DB, hence api call to register
			 * 
			 * After the above code is done, this header will not be present.
			 * 
			 * */
			FilterErrorResponse errorResponse = new FilterErrorResponse();
			String firstTimeCheck = request.getHeader("first-time-validation");
			if(StringUtils.hasText(firstTimeCheck) && firstTimeCheck.equals("true")) {
				filterChain.doFilter(request, response);
				return;
			}else {
				String jwt = getJwtFromRequest(request);
				if(StringUtils.hasText(jwt)){
					if (tokenProvider.validateJwtToken(jwt)) {
						String userId = tokenProvider.getUserIdFromToken(jwt);
						User userDetail = customUserDetailService.loadUserByUserId(userId);
						Authentication auth = new UsernamePasswordAuthenticationToken(userDetail,null,Collections.emptyList());
						SecurityContextHolder.getContext().setAuthentication(auth);
						if( userDetail == null) {
							throw new UsernameNotFoundException("Invalid Session");
						}
						filterChain.doFilter(request, response);
						return;
					} else {
						SetErrorResponse(response, errorResponse);
						return;
					}
				}
				else {
					filterChain.doFilter(request, response);
					return;
				}

			}

		}catch (Exception e) {
			logger.info("Exception in filter: {}", e.getMessage());
		}
		FilterErrorResponse errorResponse = new FilterErrorResponse();
		SetErrorResponse(response, errorResponse);
		return;
	}

	private void SetErrorResponse(HttpServletResponse response, FilterErrorResponse errorResponse) throws IOException {
		errorResponse.setBody(null);
		errorResponse.setMessage("You are not authorized to access this page!");
		errorResponse.setStatus(String.valueOf(HttpStatus.UNAUTHORIZED));
		errorResponse.setError(true);

		String jsonResp = new Gson().toJson(errorResponse);
		response.setContentType("application/json");
		response.setStatus(401);
		response.getWriter().print(jsonResp);
		return;
	}
}
