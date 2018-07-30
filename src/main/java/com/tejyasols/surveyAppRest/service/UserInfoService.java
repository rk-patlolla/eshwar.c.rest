package com.tejyasols.surveyAppRest.service;

import com.tejyasols.surveyAppRest.entity.UserInfo;

public interface UserInfoService {
	
	public UserInfo addUser(UserInfo userinfo);
	
	public UserInfo findByEmail(String email);
	
	public UserInfo findByUserName(String userName);

}
