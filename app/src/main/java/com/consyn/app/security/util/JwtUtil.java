package com.consyn.app.security.util;

import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;

import com.consyn.app.constants.Constants;
import com.consyn.app.security.service.UserDetailsImpl;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtUtil {

	private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);

	@Value("${consyn.app.jwtSecret}")
	private String jwtSecret;

	@Value("${consyn.app.jwtExpirationMS}")
	private int jwtExpirationMs;

	// while creating the token -
	// 1. Define claims of the token, like Issuer, Expiration, Subject, and the ID
	// 2. Sign the JWT using the HS512 algorithm and secret key.
	// 3. According to JWS Compact
	// Serialization(https://tools.ietf.org/html/draft-ietf-jose-json-web-signature-41#section-3.1)
	// compaction of the JWT to a URL-safe string

	public String generateToken(Authentication authentication) {
		
		UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

		return Jwts.builder()
				.claim(Constants.TOKEN_TYPE_KEY, Constants.TOKEN_TYPE_LOGIN)
				.setSubject((userPrincipal.getEmail()))
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
				.signWith(SignatureAlgorithm.HS512, jwtSecret)
				.compact();
	}

	private String generatePasswordResetJwtToken(String userId) {

		return Jwts.builder()
				.claim(Constants.TOKEN_TYPE_KEY, Constants.TOKEN_TYPE_PWD_RESET)
				.setSubject((userId))
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
				.signWith(SignatureAlgorithm.HS512, jwtSecret)
				.compact();
	}

	// validate token
	public boolean validateToken(String token) {
		try {
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
			return true;
		} catch (SignatureException e) {
			logger.error("Invalid JWT signature.", e.getMessage());
		} catch (MalformedJwtException e) {
			logger.error("Invalid JWT token.", e.getMessage());
		} catch (ExpiredJwtException e) {
			logger.error("Expired JWT token.", e.getMessage());
		} catch (UnsupportedJwtException e) {
			logger.error("JWT token is  unsupported.", e.getMessage());
		} catch (IllegalArgumentException e) {
			logger.error("JWT claims striing  is  empty", e.getMessage());
		}
		return false;
	}

	public String getUsernameFromToken(String token) {
		return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
	}

	public String getTokenTypeFromToken(String token) {
		return (String) Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody()
				.get(Constants.TOKEN_TYPE_KEY);
	}
}
