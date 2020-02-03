package com.hcl.ms.jms.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import com.hcl.ms.jms.config.AuthServerConfig;

@Service
public class AuthServerClientImpl {
	private static final Logger logger = LoggerFactory.getLogger(AuthServerClientImpl.class);

	@Autowired
	private RestTemplateBuilder templateBuilder;

	@Autowired
	private AuthServerConfig authServerConfig;

	public boolean validate(final String authHeaderValue) {
		try {
			String authTokenValue = extractTokenValue(authHeaderValue);
			if (authTokenValue != null) {
				RestTemplate restTemplate = templateBuilder.build();
				// TODO add header if required any
				HttpEntity<String> request = new HttpEntity<String>(authTokenValue, null);;

				ResponseEntity<Boolean> response = restTemplate.postForEntity(authServerConfig.getUrl(), request,
						Boolean.class);
				if (response.getStatusCode().is2xxSuccessful() && response.getBody()) {
					return true;
				}
			} else {
				logger.info("Invalid format for supplied tokn. Rejecting request");
				return false;
			}

		} catch (Exception e) {
			logger.error("Error while validating token with auth server : ", e.getCause());
			return false;
		}

		return false;
	}

	private String extractTokenValue(String authHeaderValue) {
		if (StringUtils.hasText(authHeaderValue) && authHeaderValue.startsWith("Bearer ")) {
			return authHeaderValue.substring(7, authHeaderValue.length());
		} else {
			return null;
		}
	}

}
