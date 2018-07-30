package com.tejyasols.surveyAppRest.service;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.tejyasols.surveyAppRest.entity.Answer;
import com.tejyasols.surveyAppRest.entity.Category;
import com.tejyasols.surveyAppRest.entity.Questionnaire;
import com.tejyasols.surveyAppRest.entity.QuestionsWrapper;
import com.tejyasols.surveyAppRest.repository.QuestionnaireRepository;



@Service
public class QuestionServiceImpl implements QuestionService {
	
	public static final Logger logger = LoggerFactory.getLogger(QuestionServiceImpl.class);
	
	@Autowired
	QuestionnaireRepository questionnaireRepository;
	
	@Autowired
	EntityManager entityManager;
	
	@Autowired
	AnswerService answerService;

	@Override
	public Questionnaire createQuestion(Questionnaire question) throws Exception {
		// TODO Auto-generated method stub
		return questionnaireRepository.save(question);
	}

	@Override
	public List<Questionnaire> saveall(List<Questionnaire> questionsList) throws Exception {
		// TODO Auto-generated method stub
		try
		{
		return questionnaireRepository.saveAll(questionsList);
		}catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
		
	}

//	@Override
	
	public List<Questionnaire> findByCategoryId(Long categoryId) throws Exception {
		return questionnaireRepository.findByCategoryId(categoryId);
		// TODO Auto-generated method stub
	}

	@Override
	public Boolean deleteQuestionById(Long id) throws Exception {
		// TODO Auto-generated method stub
		try
		{
			questionnaireRepository.deleteById(id);
			return true;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			logger.error(ex.getMessage());
			return false;
		}
	}

	@Override
	public Questionnaire updateQuestionById(Questionnaire questionnaire) throws Exception {
		// TODO Auto-generated method stub
		return questionnaireRepository.save(questionnaire);
	}

	@Override
	public List<Questionnaire> findall() throws Exception {
		// TODO Auto-generated method stub
		return questionnaireRepository.findAll();
	}

	@Override
	public QuestionsWrapper findQuestionsForSurvey(Long categoryId) throws Exception {
		// TODO Auto-generated method stub
//		categoryId = new Long(952);
		List<Questionnaire> questionsList =  new ArrayList<Questionnaire>();
		List<Object> resultSet = questionnaireRepository.findQuestionsForSurvey(categoryId);
		Map<String,List<String>> questionAnswerMap = new HashMap<String,List<String>>();
		
		Iterator<Object> itr = resultSet.iterator();
		
		while(itr.hasNext()){
			   Object[] obj = (Object[]) itr.next();
			   List<String> entryList;
			   if(questionAnswerMap.keySet().contains(String.valueOf(obj[1])))
			   {
				  entryList = questionAnswerMap.get(String.valueOf(obj[1]));
			   }
			   else
			   {
				   entryList = new ArrayList<String>();
			   }
			   if(entryList.size()==0)
			   {
				   entryList.add(String.valueOf(obj[3]));
			   }
			   entryList.add(String.valueOf(obj[0]));
			   questionAnswerMap.put(String.valueOf(obj[1]), entryList);
			}
		
		for(Entry<String,List<String>> entry : questionAnswerMap.entrySet())
		{
			Questionnaire quest =new Questionnaire();
			quest.setQuestion(String.valueOf(entry.getKey()));
			List<String> aList = (List<String>)entry.getValue();
			quest.setQuestionId(Long.valueOf(aList.get(0)));
			logger.debug("questId is "+quest.getQuestionId());
			aList.remove(aList.get(0));
			List<Answer> answers = new ArrayList<Answer>();
			for(String a: aList)
			{
				Answer ans = new Answer();
				ans.setAnswer(a);
				answers.add(ans);
			}
			quest.setAnswers(answers);
			questionsList.add(quest);
		}
		questionsList.stream().forEach(q->{
			logger.debug("question is "+q.getQuestion());
			q.getAnswers().stream().forEach(a->logger.debug("assosciated answer is "+a.getAnswer()));
		});
		
		QuestionsWrapper qlw = new QuestionsWrapper();
		qlw.setQuestions(questionsList);
		return qlw;
	}

	
	 private static void printResult(Object result) {
		    if (result == null) {
		      System.out.print("NULL");
		    } else if (result instanceof Object[]) {
		      Object[] row = (Object[]) result;
		      System.out.print("[");
		      for (int i = 0; i < row.length; i++) {
		        printResult(row[i]);
		      }
		      System.out.print("]");
		    } else if (result instanceof Long || result instanceof Double
		        || result instanceof String) {
		      System.out.print(result.getClass().getName() + ": " + result);
		    } else {
		      System.out.print(result);
		    }
		    System.out.println();
		  }

	@Transactional
	@Override
	public Boolean deleteQuestionsBasedOnCategoryId(Category category) throws Exception {
		String queryStr = "Delete FROM Questionnaire q where q.category = :category";
        Query query = entityManager.createQuery(queryStr);
        int noofdeleted = query.setParameter("category", category).executeUpdate();
        if(noofdeleted>0)
        {
        	return true;
        }
        else
        {
        	return false;
        }
		
	}

	@Override
	public Boolean saveCategoryBasedQuestions(Category category) throws Exception {
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		List<Questionnaire> questionsAdded = new ArrayList<Questionnaire>();
		List<Answer> savedAnswerList = new ArrayList<Answer>();
		try
		{
		for (Questionnaire ques : category.getQuestions()) {
			Questionnaire questionnaire = new Questionnaire();
			questionnaire.setQuestion(ques.getQuestion());
			questionnaire.setCategory(category);
			questionnaire.setCreateDateTime(timestamp);
			questionnaire.setUpdateDateTime(timestamp);
			Questionnaire questionAdded = createQuestion(questionnaire);
			questionsAdded.add(questionAdded);
			List<Answer> answerList = new ArrayList<Answer>();
			for (Answer ans : ques.getAnswers()) {
				Answer a = new Answer();
				a.setAnswer(ans.getAnswer());
				a.setQuestion(questionAdded);
				a.setCreateDateTime(timestamp);
				a.setUpdateDateTime(timestamp);
				answerList.add(a);

			}
			savedAnswerList = answerService.createAnswer(answerList);
		}
		return true;
		}
		catch(DataIntegrityViolationException sqlException)
		{
			throw new DataIntegrityViolationException(""+category.getCategoryId());
		}
		catch(Exception ex)
		{
			throw new Exception(ex.getLocalizedMessage());
		}
		
		/*if(questionsAdded.size()>0&&savedAnswerList.size()>0)
		{
			return true;
		}
		return false;*/
	}
	
}
