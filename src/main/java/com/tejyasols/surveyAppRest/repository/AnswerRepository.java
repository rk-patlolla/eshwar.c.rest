package com.tejyasols.surveyAppRest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tejyasols.surveyAppRest.entity.Answer;

import java.util.*;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
	
	

}
