package com.bitbank.controllers.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bitbank.responses.v1.PingResponse;
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
	public ResponseEntity<PingResponse> index() {
		return ResponseEntity.ok(new PingResponse(pingService.show()));
	}
}
