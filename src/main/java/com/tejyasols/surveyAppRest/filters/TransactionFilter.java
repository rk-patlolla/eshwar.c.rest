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

import org.slf4j.*;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(2)
public class TransactionFilter implements Filter {
	
	public static final Logger logger = LoggerFactory.getLogger(TransactionFilter.class);

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		Timestamp ts = new Timestamp(System.currentTimeMillis());
		HttpServletRequest req = (HttpServletRequest) request;
        logger.debug(
          "Starting a transaction for req : {} : {}", 
          req.getRequestURI(),ts);
  
        chain.doFilter(request, response);
        logger.debug(
          "Committing a transaction for req : {} : {}", 
          req.getRequestURI(),ts);
		
	}
	
	@Bean
	public FilterRegistrationBean transactionFilterBean() {
	  final FilterRegistrationBean transactionFilterBean = new FilterRegistrationBean();
	  transactionFilterBean.setFilter(new TransactionFilter());
	  transactionFilterBean.addUrlPatterns("/*");
	  transactionFilterBean.setEnabled(Boolean.TRUE);
	  transactionFilterBean.setName("Meuw Filter");
	  transactionFilterBean.setAsyncSupported(Boolean.TRUE);
	  return transactionFilterBean;
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
