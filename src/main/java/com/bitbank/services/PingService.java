package com.bitbank.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PingService {
	
	@Value("${app.version}")
	private String appVersion;
	
	private String getAppVersion() {
		return appVersion;
	}
	
	public String show() {
		return getAppVersion();
		
	}
	
}
