package com.whichroomapi.domain.logic.dto;

import java.util.List;

import lombok.Data;

@Data
public class UserResponse {

	private List<UserModel> users;

}
