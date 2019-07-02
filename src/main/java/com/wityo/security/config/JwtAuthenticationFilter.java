package com.wityo.security.config;

import java.io.IOException;
import java.util.Collections;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.wityo.common.Constant;
import com.wityo.modules.user.model.User;
import com.wityo.security.service.CustomUserDetailsService;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private JwtTokenProvider provider;
	
	@Autowired
	private CustomUserDetailsService customUserDetailService;
	
	private String getJwtFromRequest(HttpServletRequest request) {
		String bearerToken = request.getHeader(Constant.HEADER_TOKEN_KEY);
		if(StringUtils.hasText(bearerToken) && bearerToken.startsWith(Constant.TOKEN_PREFIX)) {
			return bearerToken.substring(7, bearerToken.length());
		}
		return null;
	}

	/* ::::::::::::::::::::::All the request will go through this filter:::::::::::::::::::::: */
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
			String token = getJwtFromRequest(request);
			if(StringUtils.hasText(token) && provider.validateJwtToken(token)) {
				Long userId = provider.getUserIdFromToken(token);
				User userDetail = customUserDetailService.loadUserByUserId(userId);
				UsernamePasswordAuthenticationToken auth
					= new UsernamePasswordAuthenticationToken(userDetail, null, Collections.emptyList());
				auth.setDetails(new WebAuthenticationDetailsSource()
									.buildDetails(request));
			}
		}catch (Exception e) {
		}
		filterChain.doFilter(request, response);
	}

}
