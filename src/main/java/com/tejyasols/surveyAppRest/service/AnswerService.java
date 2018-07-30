package com.tejyasols.surveyAppRest.service;

import java.util.List;

import com.tejyasols.surveyAppRest.entity.Answer;
import com.tejyasols.surveyAppRest.entity.Questionnaire;


public interface AnswerService {
	
	public List<Answer> createAnswer(List<Answer> answerList) throws Exception;
	
	public Boolean deleteAnswersAssosciatedToQuestionsIn(List<Questionnaire> questionsList) throws Exception;

}
