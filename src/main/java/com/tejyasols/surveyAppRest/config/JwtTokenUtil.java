package com.tejyasols.surveyAppRest.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.tejyasols.surveyAppRest.entity.UserInfo;

import ch.qos.logback.classic.Logger;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtTokenUtil implements Serializable {

	@Value("${jwt.ACCESS_TOKEN_VALIDITY_SECONDS}")
	private String ACCESS_TOKEN_VALIDITY_SECONDS;

	@Value("${jwt.SIGNING_KEY}")
	private String SIGNING_KEY;

	public String getUsernameFromToken(String token) {
		return getClaimFromToken(token, Claims::getSubject);
	}

	public Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}

	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}

	private Claims getAllClaimsFromToken(String token) {
		return Jwts.parser().setSigningKey(SIGNING_KEY).parseClaimsJws(token).getBody();
	}

	private Boolean isTokenExpired(String token) {
		final Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}

	public String generateToken(UserInfo user) {
		return doGenerateToken(user);
	}

	private String doGenerateToken(UserInfo user) {

		Claims claims = Jwts.claims().setSubject(user.getEmail());
		claims.put("scopes", Arrays.asList(new SimpleGrantedAuthority(user.getUserRole())));
		
		Date exp = new Date(System.currentTimeMillis() + Long.valueOf(ACCESS_TOKEN_VALIDITY_SECONDS));

		System.out.println("token expires at "+exp);
		return Jwts.builder().setClaims(claims).setIssuer("http://devglan.com")
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(exp)
				.signWith(SignatureAlgorithm.HS256, SIGNING_KEY).compact();
	}

	public Boolean validateToken(String token, UserDetails userDetails) {
		final String username = getUsernameFromToken(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

}
