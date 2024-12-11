package com.whichroomapi.domain.logic;

import com.whichroomapi.domain.logic.dto.MeetingInfoResponse;
import com.whichroomapi.domain.logic.dto.UserModel;

public interface GetMeetingInfoLogic {

	public MeetingInfoResponse getMeetingInfo(UserModel user);

}
