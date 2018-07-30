package com.tejyasols.surveyAppRest.entity;

import java.io.Serializable;
import java.util.List;

public class QuestionsWrapper implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public List<Questionnaire> questions;

	public List<Questionnaire> getQuestions() {
		return questions;
	}

	public void setQuestions(List<Questionnaire> questions) {
		this.questions = questions;
	}
	
	
	
	
	

}
