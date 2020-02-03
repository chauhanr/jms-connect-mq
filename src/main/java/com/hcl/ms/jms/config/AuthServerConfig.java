package com.hcl.ms.jms.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 
 *         Configuration class to handle configurations from properties file.
 */

@Component
@ConfigurationProperties(prefix = "auth.server")
public class AuthServerConfig {

	public String url;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	
}
