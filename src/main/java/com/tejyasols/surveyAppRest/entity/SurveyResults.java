package com.tejyasols.surveyAppRest.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "SurveyResults")
@EntityListeners(AuditingEntityListener.class)
public class SurveyResults implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2568845337296151487L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "survey_results_id")
	private Long surveyResultsId;

	@ManyToOne
	@JoinColumn(name = "userId")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private UserInfo userDetails;

	@ManyToOne
	@JoinColumn(name = "questionId")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Questionnaire questionnaire;

	@Column
	private String answer;

	private ResultMetric resultMetric;

	public Long getSurveyResultsId() {
		return surveyResultsId;
	}

	public void setSurveyResultsId(Long surveyResultsId) {
		this.surveyResultsId = surveyResultsId;
	}

	public UserInfo getUserDetails() {
		return userDetails;
	}

	public void setUserDetails(UserInfo userDetails) {
		this.userDetails = userDetails;
	}

	public Questionnaire getQuestionnaire() {
		return questionnaire;
	}

	public void setQuestionnaire(Questionnaire questionnaire) {
		this.questionnaire = questionnaire;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public ResultMetric getResultMetric() {
		return resultMetric;
	}

	public void setResultMetric(ResultMetric resultMetric) {
		this.resultMetric = resultMetric;
	}

	@Override
	public String toString() {
		return "SurveyResults [surveyResultsId=" + surveyResultsId + ", userDetails=" + userDetails + ", questionnaire="
				+ questionnaire + ", answer=" + answer + ", resultMetric=" + resultMetric + "]";
	}
	
	

}
