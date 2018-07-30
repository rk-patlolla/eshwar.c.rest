package com.tejyasols.surveyAppRest.config;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class SurveyAppRestAuthenticationEntryPoint extends BasicAuthenticationEntryPoint {
	
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		 response.addHeader("WWW-Authenticate", "Basic realm="+ getRealmName());
	        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	        System.out.println("req :"+request.getUserPrincipal());
	        PrintWriter writer = response.getWriter();
	        writer.println("HTTP Status 401 - " + authException.getMessage());
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		setRealmName("Baeldung");
        super.afterPropertiesSet();
	}

}
