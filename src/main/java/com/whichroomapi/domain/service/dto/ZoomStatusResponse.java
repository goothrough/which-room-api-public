package com.whichroomapi.domain.service.dto;

import java.util.List;

import lombok.Data;

@Data
public class ZoomStatusResponse {

	private List<ZoomStatusModel> zoomStatusList;

	private String requestTimeStamp;
}
