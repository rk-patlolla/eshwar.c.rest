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
@Table(name = "ResultMetrics")
@EntityListeners(AuditingEntityListener.class)
public class ResultMetric implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1990414284288563438L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "resultMetric_id")
	private Long resultsMetricId;

	@ManyToOne
	@JoinColumn(name = "questionId")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Questionnaire questionnaire;

	@Column
	private Integer totalNoOFHits;

	@Column
	private Integer noOfYes;

	@Column
	private Integer noOfNo;

	public Questionnaire getQuestionnaire() {
		return questionnaire;
	}

	public void setQuestionnaire(Questionnaire questionnaire) {
		this.questionnaire = questionnaire;
	}

	public Integer getTotalNoOFHits() {
		return totalNoOFHits;
	}

	public void setTotalNoOFHits(Integer totalNoOFHits) {
		this.totalNoOFHits = totalNoOFHits;
	}

	public Integer getNoOfYes() {
		return noOfYes;
	}

	public void setNoOfYes(Integer noOfYes) {
		this.noOfYes = noOfYes;
	}

	public Integer getNoOfNo() {
		return noOfNo;
	}

	public void setNoOfNo(Integer noOfNo) {
		this.noOfNo = noOfNo;
	}

	public Long getResultsMetricId() {
		return resultsMetricId;
	}

	public void setResultsMetricId(Long resultsMetricId) {
		this.resultsMetricId = resultsMetricId;
	}

	@Override
	public String toString() {
		return "ResultMetric [resultsMetricId=" + resultsMetricId + ", questionnaire=" + questionnaire
				+ ", totalNoOFHits=" + totalNoOFHits + ", noOfYes=" + noOfYes + ", noOfNo=" + noOfNo + "]";
	}

	
}
