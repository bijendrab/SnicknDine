package com.wityo.security.config;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.wityo.common.Constant;
import com.wityo.modules.user.model.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtTokenProvider {
	
	Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);
	
	/*---------------------------GENERATING THE TOKEN--------------------------------*/
	
	public String generateJwtToken(User user) {
		Date now = new Date(System.currentTimeMillis());
		Date expiryDate = new Date(now.getTime() + Constant.EXPIRY_TIME);
		String userId = Long.toString(user.getUserId());
		String customerName = user.getFirstName() +" "+user.getLastName();
		Map<String, Object> claims = new HashMap<String, Object>();
		claims.put("id", userId);
		claims.put("fullName", customerName);
		claims.put("phoneNumber", user.getPhoneNumber());
		claims.put("emailId", user.getEmailId());
		claims.put("iat", now);
		claims.put("expiryTime", expiryDate);
		return Jwts.builder()
				   .setSubject(userId)
				   .setClaims(claims)
				   .setIssuedAt(now)
				   .setExpiration(expiryDate)
				   .signWith(SignatureAlgorithm.HS512, Constant.API_SECRET)
				   .compact();
	}
	
	/*---------------------------GENERATING THE TOKEN--------------------------------*/
	
	

	/*---------------------------VALIDATING THE TOKEN--------------------------------*/
	
	public boolean validateJwtToken(String token) {
		try {
			Jwts.parser()
				.setSigningKey(Constant.API_SECRET)
				.parseClaimsJws(token);
			return true;
		} catch (SignatureException e) {
		} catch (MalformedJwtException e) {			
		} catch (ExpiredJwtException e) {
		} catch (UnsupportedJwtException e) {
		} catch (IllegalArgumentException e) {}
		
		return false;
	}
	
	/*---------------------------VALIDATING THE TOKEN--------------------------------*/
	
	
	
	/*---------------------------GETTING USERID FROM THE TOKEN-------------------------*/
	
	public Long getUserIdFromToken(String token) {
		Claims claim = Jwts.parser()
						   .setSigningKey(Constant.API_SECRET)
						   .parseClaimsJws(token)
						   .getBody();
		String userId = (String)claim.get("id");
		return Long.parseLong(userId);
	}
	
	/*---------------------------GETTING USERID FROM THE TOKEN-------------------------*/
}
