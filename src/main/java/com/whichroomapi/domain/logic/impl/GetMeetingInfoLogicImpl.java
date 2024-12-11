package com.whichroomapi.domain.logic.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import com.whichroomapi.auth.TokenManager;
import com.whichroomapi.domain.logic.GetMeetingInfoLogic;
import com.whichroomapi.domain.logic.dto.MeetingInfoResponse;
import com.whichroomapi.domain.logic.dto.UserModel;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class GetMeetingInfoLogicImpl implements GetMeetingInfoLogic {

	@Value("${zoom.api.baseurl}")
	private String baseURL;

	private final TokenManager tokenManeger;

	@Override
	public MeetingInfoResponse getMeetingInfo(UserModel user) {
		RestClient restClient = RestClient.create();

		ResponseEntity<MeetingInfoResponse> response = null;

		try {
			response = restClient.get()
					.uri(baseURL + "meetings/" + user.getPmi())
					.header("Authorization", "Bearer " + tokenManeger.getAccessToken())
					.retrieve()
					.toEntity(MeetingInfoResponse.class);

		} catch (HttpClientErrorException e) {
			// If cache acccess token is expired.
			if (e.getStatusCode() == HttpStatus.UNAUTHORIZED) {
				log.info(e.getMessage());

				// Clear caches
				tokenManeger.clearCacheAccessToken();

				// Retry
				response = restClient.get()
						.uri(baseURL + "meetings/" + user.getPmi())
						.header("Authorization", "Bearer " + tokenManeger.getAccessToken())
						.retrieve()
						.toEntity(MeetingInfoResponse.class);
			}
		}

		return response.getBody();
	}

}
