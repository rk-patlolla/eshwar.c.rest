package com.tejyasols.surveyAppRest.config;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tejyasols.surveyAppRest.service.UserDetailsServiceImpl;
import com.tejyasols.surveyAppRest.util.ExceptionResponse;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Locale;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private UserDetailsServiceImpl userDetailsService;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Value("${jwt.HEADER_STRING}")
	private String HEADER_STRING;

	@Value("${jwt.TOKEN_PREFIX}")
	private String TOKEN_PREFIX;
	
	@Autowired
	public MessageSource ms;

	@Override
	protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		String header = req.getHeader(HEADER_STRING);
		String username = null;
		String authToken = null;
		if (header != null && header.startsWith(TOKEN_PREFIX)) {
			authToken = header.replace(TOKEN_PREFIX, "");
			try {
				username = jwtTokenUtil.getUsernameFromToken(authToken);
				System.out.println("fetched username is " + username);
			} catch (IllegalArgumentException e) {
				logger.error("an error occured during getting username from token", e);
			} catch (ExpiredJwtException e) {
				logger.warn("the token is expired and not valid anymore", e);
				ExceptionResponse er = new ExceptionResponse();
				er.setErrorCode(ms.getMessage("token.expired.code", null, Locale.US));
				er.setErrorMessage(ms.getMessage("token.expired", null, Locale.US));
				   byte[] responseToSend = restResponseBytes(er);
		            ((HttpServletResponse) res).setHeader("Content-Type", "application/json");
		            ((HttpServletResponse) res).setStatus(401);
		            res.getOutputStream().write(responseToSend);
		            return;
			} catch (SignatureException e) {
				logger.error("Authentication Failed. Username or Password not valid.");
			}
		} else {
			logger.warn("couldn't find bearer string, will ignore the header");
		}
		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

			UserDetails userDetails = userDetailsService.loadUserByUsername(username);

			if (jwtTokenUtil.validateToken(authToken, userDetails)) {

				// this line needs to be checked for multiple roles
				// UsernamePasswordAuthenticationToken authentication = new
				// UsernamePasswordAuthenticationToken(userDetails, null, Arrays.asList(new
				// SimpleGrantedAuthority("ROLE_ADMIN")));
				
				
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
				System.out.println("authenticated user " + username + ", setting security context");
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		}
//		System.out.println(SecurityContextHolder.getContext().getAuthentication().getAuthorities());
		chain.doFilter(req, res);
	}
	
	private byte[] restResponseBytes(ExceptionResponse exceptionResponse) throws IOException {
        String serialized = new ObjectMapper().writeValueAsString(exceptionResponse);
        return serialized.getBytes();
    }
}