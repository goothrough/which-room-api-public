package com.whichroomapi.domain.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.whichroomapi.config.Settings;
import com.whichroomapi.domain.logic.GetMeetingInfoLogic;
import com.whichroomapi.domain.logic.GetUserListLogic;
import com.whichroomapi.domain.logic.dto.MeetingInfoResponse;
import com.whichroomapi.domain.logic.dto.UserModel;
import com.whichroomapi.domain.service.GetZoomStatusService;
import com.whichroomapi.domain.service.dto.ZoomStatusModel;
import com.whichroomapi.domain.service.dto.ZoomStatusResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GetZoomStatusServiceImpl implements GetZoomStatusService {
	private final GetUserListLogic getUserListLogic;
	private final GetMeetingInfoLogic getMeetingInfoLogic;
	private final Settings settings;

	private final String DEFAULT_DISPLAY_ROOM_NAME = "UNKNOWN";

	@Override
	public ZoomStatusResponse getZoomStatus() {
		Map<String, String> PMIToDisplayRoomName = settings.getPMIToDisplayRoomName();

		// Get the information of users who belong to the owner account.
		List<UserModel> userList = getUserListLogic.getUserList();

		// Get the infomation of personal meeting room of each user.
		List<ZoomStatusModel> zoomStatusList = new ArrayList<ZoomStatusModel>();
		userList.forEach(user -> {
			MeetingInfoResponse meetingInfoResponse = getMeetingInfoLogic.getMeetingInfo(user);
			ZoomStatusModel zoomStatus = new ZoomStatusModel();
			zoomStatus.setUserName(user.getDisplay_name());
			zoomStatus.setRoomName(meetingInfoResponse.getTopic());
			zoomStatus.setRoomDisplayName(PMIToDisplayRoomName.computeIfAbsent(meetingInfoResponse.getId().toString(),
					key -> DEFAULT_DISPLAY_ROOM_NAME));
			zoomStatus.setRoomStatus(meetingInfoResponse.getStatus());
			zoomStatus.setJoinUrl(meetingInfoResponse.getJoin_url());
			zoomStatusList.add(zoomStatus);
		});

		ZoomStatusResponse response = new ZoomStatusResponse();
		response.setZoomStatusList(zoomStatusList.stream().sorted((a, b) -> {
			return a.getRoomDisplayName().compareTo(b.getRoomDisplayName());
		}).toList());
		response.setRequestTimeStamp(getCurrentTimestampString());

		return response;

	}

	private String getCurrentTimestampString() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date today = new Date();
		return sdf.format(today);
	}

}
