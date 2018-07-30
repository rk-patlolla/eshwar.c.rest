package com.tejyasols.surveyAppRest.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Entity
@Table(name = "Questionnaire")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties({ "model", "seatingCapacity" })
@JsonInclude(Include.NON_NULL)
public class Questionnaire implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 630642182724268139L;

	@Id
	@SequenceGenerator(name = "seq_contacts", sequenceName = "seq_contacts")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "seq_contacts")
	@Column(name = "questionId")
	@JsonProperty("questionId")
	private Long questionId;

	@Column
	@NotBlank
	@JsonProperty("question")
	private String question;

	@Column(name = "created_date", nullable = false, updatable = false)
	@JsonIgnore
	private Timestamp createDateTime;

	@Column(name = "modified_date", nullable = false, updatable = true)
	@JsonIgnore
	private Timestamp updateDateTime;

	@Column
	private boolean isActive = false;

	@ManyToOne
	//@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name = "categoryId")
	@JsonProperty("category")
	public Category category;
	
	@Transient
	@OneToMany(mappedBy="question", cascade = CascadeType.ALL, orphanRemoval = true)
	public List<Answer> answers;
	
	public String answer;

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public Long getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Long questionId) {
		this.questionId = questionId;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
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

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}
	
	

	public List<Answer> getAnswers() {
		return answers;
	}

	public void setAnswers(List<Answer> answers) {
		this.answers = answers;
	}

	@Override
	public String toString() {
		return "Questionnaire [questionId=" + questionId + ", question=" + question + ", createDateTime="
				+ createDateTime + ", updateDateTime=" + updateDateTime + ", isActive=" + isActive + ", category="
				+ category + ", answers=" + answers + "]";
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}


	

}
