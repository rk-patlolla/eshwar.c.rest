package com.tejyasols.surveyAppRest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tejyasols.surveyAppRest.entity.UserInfo;


@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, Long>
{
	@Query("SELECT u FROM UserInfo u where email = :email")
	UserInfo findByEmail(@Param("email") String email);
	
	UserInfo findByUserName(String userName);

}
