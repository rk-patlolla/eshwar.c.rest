package com.tejyasols.surveyAppRest.service;

import java.util.List;

import com.tejyasols.surveyAppRest.entity.Category;
import com.tejyasols.surveyAppRest.entity.Questionnaire;
import com.tejyasols.surveyAppRest.entity.QuestionsWrapper;


public interface QuestionService {
	
	public Questionnaire createQuestion(Questionnaire question) throws Exception;
	
	public Boolean deleteQuestionById(Long id) throws Exception;
	
	public Questionnaire updateQuestionById(Questionnaire questionnaire) throws Exception;

	public List<Questionnaire> saveall(List<Questionnaire> questionsList) throws Exception;
	
	public List<Questionnaire> findall() throws Exception;
	
	public List<Questionnaire> findByCategoryId(Long categoryId) throws Exception;
	
	public QuestionsWrapper findQuestionsForSurvey(Long categoryId) throws Exception;
	
	public Boolean deleteQuestionsBasedOnCategoryId(Category category) throws Exception;
	
	public Boolean saveCategoryBasedQuestions(Category category) throws Exception;

}
