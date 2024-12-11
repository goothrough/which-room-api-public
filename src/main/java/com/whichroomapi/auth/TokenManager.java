package com.whichroomapi.auth;

import java.util.Base64;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import com.whichroomapi.auth.dto.AccessTokenResponse;
import com.whichroomapi.config.Settings;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class TokenManager {

	private final Settings settings;

	@Value("${zoom.accesstoken.baseurl}")
	private String BASE_URL;

	@Cacheable("accessToken")
	public String getAccessToken() {
		RestClient restClient = RestClient.create();

		AccessTokenResponse response = restClient.post()
				.uri(BASE_URL + settings.getAccountId())
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.header("Authorization", generateAuthorization())
				.retrieve()
				.body(AccessTokenResponse.class);

		return response.getAccess_token();
	}

	@CacheEvict("accessToken")
	public void clearCacheAccessToken() {
		log.info("Token cache is cleared.");
	}

	/**
	 * Adding a colon between the client ID and the client secret, encode them to Base64 format 
	 * @return Authorization String for HTTP header
	 */
	private String generateAuthorization() {
		String beforeEncodingString = settings.getClientId() + ":" + settings.getClientSecret();

		return "Basic " + Base64.getEncoder().encodeToString(beforeEncodingString.getBytes());
	}

}
