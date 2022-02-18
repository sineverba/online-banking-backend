package com.bitbank.controllers.v1;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bitbank.services.v1.PingService;

@RestController
@RequestMapping("/api/v1/ping")
public class PingController {

	@Autowired
	private PingService pingService;
	
	@Autowired
	public PingController(PingService pingService) {
		this.pingService = pingService;
	}
	
	@GetMapping
	public Map<String, String> index() {
		
		Map<String, String> map = new LinkedHashMap<>();
		
		map.put("version", pingService.show());
		
		return map;
	}
}
