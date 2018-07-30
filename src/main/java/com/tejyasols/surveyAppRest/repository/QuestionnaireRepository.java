package com.tejyasols.surveyAppRest.repository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.tejyasols.surveyAppRest.entity.Questionnaire;


@Repository
@Transactional
public interface QuestionnaireRepository extends JpaRepository<Questionnaire, Long> {

/*	@PersistenceContext
	public EntityManager em;*/
	
//	select a.Answer,a.question.question,a.question.category.categoryName from Answer a;
	

	@Query("SELECT q FROM Questionnaire q where category_id = :categoryId")
	List<Questionnaire> findByCategoryId(@Param("categoryId") Long CategoryId);
	
	@Query("select a.Answer,a.question.question,a.question.category.categoryName,a.question.questionId from Answer a")
	List<Object> findQuestionsForSurvey(@Param("categoryId") Long categoryId);
	

	/*
	 * //public Category
	 * 
	 * @Query("Select q.question,a.Answer from Questionnaire q join Answer a on a.question=q.question_id and q.category_id = :categoryId"
	 * ) List<Object> findQuestionsForSurvey(@Param("categoryId") Long categoryId);
	 */

	/*public List<Object> findQuestionsForSurvey(Long categoryId)
	{
		
	}*/

}
