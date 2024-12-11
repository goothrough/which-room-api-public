package com.whichroomapi.domain.service.dto;

import lombok.Data;

@Data
public class ZoomStatusModel {

	private String userName;

	private String roomName;

	private String roomDisplayName;

	private String roomStatus;

	private String joinUrl;

}
