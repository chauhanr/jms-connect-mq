package com.hcl.ms.jms.security;

import java.io.IOException;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

//@Component
public class JwtFilter implements Filter{
	private static final Logger logger = LoggerFactory.getLogger(JwtFilter.class);

	@Autowired
	private AuthServerClientImpl client;

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;

		logger.info("Starting a transaction for req : {}", httpRequest.getRequestURI());

		String authToken = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);

		if (client.validate(authToken)) {
			logger.debug("valid token proceeding for further processing");
			chain.doFilter(servletRequest, servletResponse);
		} else {
			logger.info("Invalid token: Rejecting request");
			HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
			httpResponse.reset();
			httpResponse.setStatus(HttpStatus.FORBIDDEN.value());
		}
	}

	@Override
	public void init(FilterConfig config) throws ServletException{

	}

}
