package com.whichroomapi.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.whichroomapi.domain.service.GetZoomStatusService;
import com.whichroomapi.domain.service.dto.ZoomStatusResponse;

import lombok.RequiredArgsConstructor;

@RestController
@CrossOrigin
@RequiredArgsConstructor
public class ZoomApiRequestController {
	private final GetZoomStatusService service;

	@PostMapping("/getZoomStatus")
	public ZoomStatusResponse getZoomStatus() {

		return service.getZoomStatus();

	}

}
