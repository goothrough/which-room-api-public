package com.whichroomapi.config;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "settings")
public class Settings {

	private Map<String, String> PMIToDisplayRoomName;

	private String accountId;

	private String clientId;

	private String clientSecret;

	public void setPMIToDisplayRoomName(Map<String, String> pmitodisplayroomname) {
		this.PMIToDisplayRoomName = pmitodisplayroomname;
	}

	public Map<String, String> getPMIToDisplayRoomName() {
		return this.PMIToDisplayRoomName;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountid) {
		this.accountId = accountid;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientid) {
		this.clientId = clientid;
	}

	public String getClientSecret() {
		return clientSecret;
	}

	public void setClientSecret(String clientsecret) {
		this.clientSecret = clientsecret;
	}

}
