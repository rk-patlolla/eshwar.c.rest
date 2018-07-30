package com.tejyasols.surveyAppRest.controller;

import java.util.Locale;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tejyasols.surveyAppRest.config.JwtTokenUtil;
import com.tejyasols.surveyAppRest.entity.AuthToken;
import com.tejyasols.surveyAppRest.entity.UserInfo;
import com.tejyasols.surveyAppRest.service.UserInfoService;
import com.tejyasols.surveyAppRest.util.ExceptionResponse;

@RestController
@RequestMapping("/token")
public class AuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private UserInfoService userService;
	

	@RequestMapping(value = "/generate-token", method = RequestMethod.POST)
	public ResponseEntity<?> register(@RequestBody UserInfo loginUser) throws AuthenticationException {
		try {
			final Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginUser.getEmail(), loginUser.getPassword()));
			SecurityContextHolder.getContext().setAuthentication(authentication);
			final UserInfo user = userService.findByEmail(loginUser.getEmail());
			final String token = jwtTokenUtil.generateToken(user);
			return ResponseEntity.ok(new AuthToken(token));
		} catch (AuthenticationException ex) {
			ExceptionResponse exceptionResponse = new ExceptionResponse();
			exceptionResponse.setErrorCode(String.valueOf(HttpServletResponse.SC_CONFLICT));
			exceptionResponse.setErrorMessage(ex.getLocalizedMessage());
			exceptionResponse.setErrors(null);
			return new ResponseEntity<>(exceptionResponse, HttpStatus.EXPECTATION_FAILED);
		}
	}

}
