package com.whichroomapi.domain.logic.dto;

import lombok.Data;

@Data
public class MeetingInfoResponse {

	private String topic;

	private String status;

	private Long id;

	private String join_url;

}
