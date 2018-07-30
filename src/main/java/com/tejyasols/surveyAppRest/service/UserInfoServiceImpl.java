package com.tejyasols.surveyAppRest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tejyasols.surveyAppRest.entity.UserInfo;
import com.tejyasols.surveyAppRest.repository.UserInfoRepository;


@Service
public class UserInfoServiceImpl implements UserInfoService {
	
	@Autowired
	UserInfoRepository userInfoRepository;

	@Override
	public UserInfo addUser(UserInfo userinfo) {
		// TODO Auto-generated method stub
		return userInfoRepository.save(userinfo);
	}

	@Override
	public UserInfo findByEmail(String email) {
		// TODO Auto-generated method stub
		return userInfoRepository.findByEmail(email);
	}

	@Override
	public UserInfo findByUserName(String userName) {
		// TODO Auto-generated method stub
		return userInfoRepository.findByUserName(userName);
	}

}
