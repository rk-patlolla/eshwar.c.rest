package com.tejyasols.surveyAppRest.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.tejyasols.surveyAppRest.entity.UserInfo;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {
	
	public static final Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);
	
	@Autowired
	UserInfoService userInfoService;
	
	@Autowired
	BCryptPasswordEncoder bcrypt;

	@Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		logger.debug("email in request is "+email);
//        UserInfo appUser = this.userInfoService.findByEmail(email);
		UserInfo appUser = this.userInfoService.findByEmail(email);
 
        if (appUser == null) {
            System.out.println("Email not found! " + email);
            throw new UsernameNotFoundException("User with email " + email + " was not found in the database");
        }
 
        logger.debug("Found User: " + appUser);
 
        // [ROLE_USER, ROLE_ADMIN,..]
 
        List<GrantedAuthority> grantList = new ArrayList<GrantedAuthority>();
                GrantedAuthority authority = new SimpleGrantedAuthority(appUser.getUserRole());
                grantList.add(authority);
 
        UserDetails userDetails = (UserDetails) new User(appUser.getEmail(), //
                appUser.getPassword(), grantList);
 
        return userDetails;
    }

}
