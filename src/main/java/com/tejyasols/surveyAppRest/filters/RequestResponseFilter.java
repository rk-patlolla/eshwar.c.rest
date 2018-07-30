package com.tejyasols.surveyAppRest.filters;

import java.io.IOException;
import java.sql.Timestamp;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class RequestResponseFilter implements Filter {
	
	public static final Logger logger = LoggerFactory.getLogger(RequestResponseFilter.class);

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        logger.debug(
          "Logging Request  {} : {} : {}", req.getMethod(), 
          req.getRequestURI(),ts);
        chain.doFilter(request, response);
        logger.debug(
          "Logging Response :{} : {}", 
          res.getContentType(),ts);
		
	}
	
	@Bean
	public FilterRegistrationBean myFilterBean() {
	  final FilterRegistrationBean filterRegBean = new FilterRegistrationBean();
	  filterRegBean.setFilter(new RequestResponseFilter());
	  filterRegBean.addUrlPatterns("/*");
	  filterRegBean.setEnabled(Boolean.TRUE);
	  filterRegBean.setName("Meu Filter");
	  filterRegBean.setAsyncSupported(Boolean.TRUE);
	  return filterRegBean;
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

}
