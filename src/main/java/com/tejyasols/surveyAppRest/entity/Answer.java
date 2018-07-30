package com.tejyasols.surveyAppRest.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Entity
@Table(name = "answer")
@JsonInclude(Include.NON_NULL)
public class Answer {

	@Id
	@SequenceGenerator(name = "seq_contacts", sequenceName = "seq_contacts")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "seq_contacts")
	@JsonProperty("answerId")
	private Long Answer_ID;

	@Column
	@JsonProperty("answer")
	private String Answer;

	@ManyToOne
	@JoinColumn(name = "questionId")
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonProperty("question")
	private Questionnaire question;

	@Column(name = "created_date", nullable = false, updatable = false)
	@JsonIgnore
	private Timestamp createDateTime;

	@Column(name = "modified_date", nullable = false, updatable = true)
	@JsonIgnore
	private Timestamp updateDateTime;

	public Answer() {

	}

	public Answer(Long Answer_ID, String Answer, Questionnaire question) {
		super();
		this.Answer_ID = Answer_ID;
		this.Answer = Answer;
		this.question = question;
	}

	public Long getAnswer_ID() {
		return Answer_ID;
	}

	public void setAnswer_ID(Long answer_ID) {
		Answer_ID = answer_ID;
	}

	public String getAnswer() {
		return Answer;
	}

	public void setAnswer(String answer) {
		Answer = answer;
	}

	public Questionnaire getQuestion() {
		return question;
	}

	public void setQuestion(Questionnaire question) {
		this.question = question;
	}

	public Timestamp getCreateDateTime() {
		return createDateTime;
	}

	public void setCreateDateTime(Timestamp createDateTime) {
		this.createDateTime = createDateTime;
	}

	public Timestamp getUpdateDateTime() {
		return updateDateTime;
	}

	public void setUpdateDateTime(Timestamp updateDateTime) {
		this.updateDateTime = updateDateTime;
	}

	
	
}
