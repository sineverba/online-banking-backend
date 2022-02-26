package com.bitbank.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bitbank.responses.PingResponse;
import com.bitbank.services.PingService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/ping")
@Tag(name = "Ping", description = "Ping")
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
