	package com.tejyasols.surveyAppRest.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.feed.RssChannelHttpMessageConverter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.tejyasols.surveyAppRest.entity.Category;
import com.tejyasols.surveyAppRest.entity.QuestionsWrapper;
import com.tejyasols.surveyAppRest.entity.SurveyResults;
import com.tejyasols.surveyAppRest.entity.UserInfo;
import com.tejyasols.surveyAppRest.service.CategoryService;
import com.tejyasols.surveyAppRest.service.QuestionService;
import com.tejyasols.surveyAppRest.service.SurveyResultsService;
import com.tejyasols.surveyAppRest.service.UserInfoService;
import com.tejyasols.surveyAppRest.util.ResponseMapper;


@RestController
@RequestMapping("/user")
public class UserController {
	
	public static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	UserInfoService userInfoService;
	
	@Autowired
	CategoryService categoryService;
	
	@Autowired
	QuestionService questionService; 
	
	@Autowired
	SurveyResultsService surveyResultsService;
	
/*	@Autowired
	BCryptPasswordEncoder bcryptPassworsEncoder;*/
	
	@RequestMapping("/userCreationForm")
	public ModelAndView userCreationForm()
	{
		return new ModelAndView("addUser","command",new UserInfo());
	}
	
	@PreAuthorize("hasRole('USER')")
	@RequestMapping(value = "/addUser",method = RequestMethod.POST , produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<ResponseMapper> addUser(@RequestBody UserInfo userInfo)
	{
		ResponseMapper responseMapper = new ResponseMapper();
		try
		{
		userInfo.setUserRole("USER");
		userInfo.setPassword(new BCryptPasswordEncoder().encode(userInfo.getPassword()));
		UserInfo userAdded = userInfoService.addUser(userInfo);
		responseMapper.setObject(userAdded);
		responseMapper.setMessage(HttpStatus.ACCEPTED.getReasonPhrase());
		responseMapper.setResponseCode(HttpStatus.ACCEPTED);
		return new ResponseEntity<ResponseMapper>(responseMapper,HttpStatus.ACCEPTED);
		}
		catch(Exception e)
		{
			responseMapper.setObject(null);
			responseMapper.setResponseCode(HttpStatus.EXPECTATION_FAILED);
			e.printStackTrace();
			responseMapper.setMessage(e.getMessage());
			return new ResponseEntity<ResponseMapper>(responseMapper, HttpStatus.EXPECTATION_FAILED);	
		}
	}
	
	/*@GetMapping("/getAllCategories")
	public ModelAndView getAllCategories(@ModelAttribute("Category") Category category,BindingResult result) {
		List<Category> categoriesList = new ArrayList<Category>();
		try {
			categoriesList = categoryService.findAll();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ModelAndView mv = new ModelAndView("getAllCategoriesForSurvey");
		mv.addObject("Categories", categoriesList);
		return mv;
	}
*/
	@RequestMapping(value = "/selectCategoryForSurvey/{categoryId}",method = RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<ResponseMapper> listQuestionsForSurvey(@PathVariable Long categoryId) throws Exception
	{
		ResponseMapper responseMapper = new ResponseMapper();
		logger.debug("entered selectCategoryForSurvey");
		
		try {
			QuestionsWrapper qlw = questionService.findQuestionsForSurvey(categoryId);
			responseMapper.setObject(qlw);
			responseMapper.setResponseCode(HttpStatus.ACCEPTED);
			responseMapper.setMessage(HttpStatus.ACCEPTED.getReasonPhrase());
			return new ResponseEntity<ResponseMapper>(responseMapper,HttpStatus.ACCEPTED);
		}
		catch(Exception e)
		{
			responseMapper.setObject(null);
			responseMapper.setResponseCode(HttpStatus.EXPECTATION_FAILED);
			e.printStackTrace();
			responseMapper.setMessage(e.getMessage());
			return new ResponseEntity<ResponseMapper>(responseMapper, HttpStatus.EXPECTATION_FAILED);	
		}
	}
	
	
	@RequestMapping(value = "/saveSurvey",method = RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<ResponseMapper> saveSurvey(@RequestBody QuestionsWrapper qw) throws Exception
	{
		ResponseMapper responseMapper = new ResponseMapper();
		logger.debug("called saveSurvey");
		try
		{
		List<SurveyResults> survey = new ArrayList<SurveyResults>();
		qw.getQuestions().forEach(q->{
			logger.debug("each question answered "+q.getQuestion());
			logger.debug("it answer is" +q.getAnswer());
		});
		responseMapper.setObject(surveyResultsService.saveSurvey(qw));
		responseMapper.setResponseCode(HttpStatus.ACCEPTED);
		responseMapper.setMessage(HttpStatus.ACCEPTED.getReasonPhrase());
		return new ResponseEntity<ResponseMapper>(responseMapper,HttpStatus.ACCEPTED);
		}
		catch(Exception e)
		{
			responseMapper.setObject(null);
			responseMapper.setResponseCode(HttpStatus.EXPECTATION_FAILED);
			e.printStackTrace();
			responseMapper.setMessage(e.getMessage());
			return new ResponseEntity<ResponseMapper>(responseMapper, HttpStatus.EXPECTATION_FAILED);	
		}
	}
	
}

	

