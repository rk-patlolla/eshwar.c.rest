package com.tejyasols.surveyAppRest.service;

import java.util.List;

import com.tejyasols.surveyAppRest.entity.QuestionsWrapper;
import com.tejyasols.surveyAppRest.entity.SurveyResults;

public interface SurveyResultsService {
	
	public List<SurveyResults> saveSurvey(QuestionsWrapper qw) throws Exception;

}
