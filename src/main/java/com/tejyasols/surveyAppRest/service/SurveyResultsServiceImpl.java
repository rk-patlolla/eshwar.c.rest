package com.tejyasols.surveyAppRest.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tejyasols.surveyAppRest.entity.Questionnaire;
import com.tejyasols.surveyAppRest.entity.QuestionsWrapper;
import com.tejyasols.surveyAppRest.entity.SurveyResults;
import com.tejyasols.surveyAppRest.entity.UserInfo;
import com.tejyasols.surveyAppRest.repository.SurveyResultsRepository;


@Service
public class SurveyResultsServiceImpl implements SurveyResultsService {
	
	public static final Logger logger = LoggerFactory.getLogger(SurveyResultsServiceImpl.class);
	
	@Autowired
	SurveyResultsRepository surveyResultsRepository;

	@Override
	public List<SurveyResults> saveSurvey(QuestionsWrapper qw) throws Exception {
		// TODO Auto-generated method stub
		List<SurveyResults> surveyResults = new ArrayList<SurveyResults>();
		for(Questionnaire q:qw.getQuestions())
		{
			SurveyResults sr = new SurveyResults();
			Questionnaire quest = new Questionnaire();
			UserInfo user = new UserInfo();
			quest.setQuestionId(q.getQuestionId());
			user.setId(new Long(1));
			sr.setQuestionnaire(quest);
			sr.setUserDetails(user);
			sr.setAnswer(q.getAnswer());
			logger.debug("quest="+sr.getQuestionnaire()+":::user="+sr.getUserDetails()+":::Answer");
			surveyResults.add(sr);
		}
		/*qw.getQuestions().forEach(q->{
			SurveyResults sr = new SurveyResults();
			Questionnaire quest = new Questionnaire();
			UserDetails user = new UserDetails();
			quest.setQuestionId(q.getQuestionId());
			user.setId(new Long(1));
			sr.setQuestionnaire(quest);
			sr.setUserDetails(user);
			sr.setAnswer(q.getAnswer());
			surveyResults.add(sr);
		});*/
//		return surveyResultsRepository.saveAll(surveyResults);
		logger.debug("surveyResultsList size="+surveyResults.size());
		List<SurveyResults> savedSurveyResults = surveyResultsRepository.saveAll(surveyResults);
		logger.debug("No od sr saved"+savedSurveyResults.size());
		return null;
	}

}
