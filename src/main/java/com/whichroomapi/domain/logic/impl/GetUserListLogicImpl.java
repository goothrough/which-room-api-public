package com.whichroomapi.domain.logic.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import com.whichroomapi.auth.TokenManager;
import com.whichroomapi.domain.logic.GetUserListLogic;
import com.whichroomapi.domain.logic.dto.UserModel;
import com.whichroomapi.domain.logic.dto.UserResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class GetUserListLogicImpl implements GetUserListLogic {

	@Value("${zoom.api.baseurl}")
	private String BASE_URL;

	private final TokenManager tokenManeger;

	@Override
	@Cacheable("userList")
	public List<UserModel> getUserList() {

		RestClient restClient = RestClient.create();

		ResponseEntity<UserResponse> response = null;

		try {
			response = restClient.get()
					.uri(BASE_URL + "users")
					.header("Authorization", "Bearer " + tokenManeger.getAccessToken())
					.header("Content-Type", MediaType.APPLICATION_JSON.getType())
					.retrieve()
					.toEntity(UserResponse.class);

		} catch (HttpClientErrorException e) {
			// If cache acccess token is expired.
			if (e.getStatusCode() == HttpStatus.UNAUTHORIZED) {
				log.info(e.getMessage());

				// Clear caches
				tokenManeger.clearCacheAccessToken();

				clearCacheUserList();

				// Retry
				response = restClient.get()
						.uri(BASE_URL + "users")
						.header("Authorization", "Bearer " + tokenManeger.getAccessToken())
						.header("Content-Type", MediaType.APPLICATION_JSON.getType())
						.retrieve()
						.toEntity(UserResponse.class);

			}
		}

		return response.getBody().getUsers();
	}

	@CacheEvict("userList")
	private void clearCacheUserList() {
		log.info("User List cache is cleared.");
	}

}
