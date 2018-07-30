package com.tejyasols.surveyAppRest.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tejyasols.surveyAppRest.entity.Answer;
import com.tejyasols.surveyAppRest.entity.Questionnaire;
import com.tejyasols.surveyAppRest.repository.AnswerRepository;


@Service
public class AnswerServiceImpl implements AnswerService {
	
	private static final Logger logger = LoggerFactory.getLogger(AnswerServiceImpl.class);
	
	@Autowired
	AnswerRepository answerRepositry;
	
//	EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("persistence");
	@Autowired
	EntityManager entityManager;
	

	@Override
	public List<Answer> createAnswer(List<Answer> answerList) throws Exception {
		// TODO Auto-generated method stub
		return answerRepositry.saveAll(answerList);
	}

	@Transactional
	@Override
	public Boolean deleteAnswersAssosciatedToQuestionsIn(List<Questionnaire> questionsList) throws Exception {
		StringBuilder sb = new StringBuilder();
		logger.debug("quessize is "+questionsList.size());
		questionsList.stream().forEach(q->{
			logger.debug("qid is "+q.getQuestionId());
			sb.append(q.getQuestionId()).append(",");
		});
		String queryStr = "Delete FROM Answer a where a.question in (?1)";
		//logger.debug("sb is "+sb.toString());
        Query query = entityManager.createQuery(queryStr);
        String idList = sb.toString();
        int noofdeleted = query.setParameter(1, questionsList).executeUpdate();
        if(noofdeleted>0)
        {
        	return true;
        }
        else
        {
        	return false;
        }
	}

}
