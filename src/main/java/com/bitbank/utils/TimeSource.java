package com.bitbank.utils;

import java.io.Serializable;

import org.springframework.stereotype.Component;

/**
 * 
 * This class returns current DateTime (or other time).
 * 
 * It is useful for Unit Testing.
 * 
 * @author sineverba
 *
 */
@Component
public class TimeSource implements Serializable {
	
	private static final long serialVersionUID = -20220210203900L;
	
	public Long getCurrentTimeMillis() {
		return System.currentTimeMillis();
	}
	
}
