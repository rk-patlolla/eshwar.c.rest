package com.tejyasols.surveyAppRest.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.*;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "Category")
@EntityListeners(AuditingEntityListener.class)
@JsonInclude(Include.NON_NULL)
public class Category implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7626275836587449141L;

	@NotBlank
	@JsonProperty("categoryName")
	private String categoryName;

	@Column(name = "created_date", nullable = false, updatable = false)
	@JsonIgnore
	@CreatedDate
	private Timestamp createDateTime;

	@Id
	@SequenceGenerator(name = "seq_contacts", sequenceName = "seq_contacts")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "seq_contacts")
	@JsonProperty("categoryId")
	private Long categoryId;

	@Column
	@JsonIgnore
	private boolean isActive = false;

	@Column(name = "modified_date")
	@JsonIgnore
	@LastModifiedDate
	private Timestamp updateDateTime;

	@Column(name = "created_by")
	@CreatedBy
	private String createdBy;

	@Column(name = "modified_by")
	@LastModifiedBy
	private String modifiedBy;

	@Transient
	@OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonProperty("questions")
	private List<Questionnaire> questions;

	public Category() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Category(Long categoryId, @NotBlank String categoryName) {
		super();
		this.categoryId = categoryId;
		this.categoryName = categoryName;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public Timestamp getCreateDateTime() {
		return createDateTime;
	}

	public Timestamp getUpdateDateTime() {
		return updateDateTime;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public void setCreateDateTime(Timestamp createDateTime) {
		this.createDateTime = createDateTime;
	}

	public void setUpdateDateTime(Timestamp updateDateTime) {
		this.updateDateTime = updateDateTime;
	}

	public void merge(Category other) {
		setCategoryName(other.getCategoryName());
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public List<Questionnaire> getQuestions() {
		return questions;
	}

	public void setQuestions(List<Questionnaire> questions) {
		this.questions = questions;
	}

	@Override
	public String toString() {
		return "Category [categoryName=" + categoryName + ", createDateTime=" + createDateTime + ", categoryId="
				+ categoryId + ", isActive=" + isActive + ", updateDateTime=" + updateDateTime + ", questions="
				+ questions + "]";
	}

}
