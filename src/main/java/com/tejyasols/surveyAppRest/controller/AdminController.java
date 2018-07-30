package com.tejyasols.surveyAppRest.controller;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.tejyasols.surveyAppRest.entity.Category;
import com.tejyasols.surveyAppRest.entity.Questionnaire;
import com.tejyasols.surveyAppRest.service.AnswerService;
import com.tejyasols.surveyAppRest.service.CategoryService;
import com.tejyasols.surveyAppRest.service.QuestionService;
import com.tejyasols.surveyAppRest.util.ResponseMapper;

@RestController
@RequestMapping("/admin")
public class AdminController {

	public static final Logger logger = LoggerFactory.getLogger(AdminController.class);

	@Autowired
	CategoryService categoryService;

	@Autowired
	QuestionService questionService;

	@Autowired
	AnswerService answerService;

	@GetMapping("/home")
	public ModelAndView index() {
		List<Category> catList = new ArrayList<Category>();
		try {
			catList = categoryService.findAll();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return new ModelAndView("home");
	}

	@GetMapping("/adminView")
	public ModelAndView admin() {
		return new ModelAndView("admin");
	}

	@RequestMapping("/createCategoryForm")
	public ModelAndView createCategoryForm() {
		return new ModelAndView("createCategoryForm", "command", new Category());
	}

	/*
	 * @GetMapping("/getAllCategories") public List<Category> getAllCategories() {
	 * List<Category> categoriesList = new ArrayList<Category>(); try {
	 * categoriesList = categoryService.findAll(); } catch (Exception e) { // TODO
	 * Auto-generated catch block e.printStackTrace(); } return categoriesList; }
	 */

	@RequestMapping(value = "/getCategory/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<ResponseMapper> getCategoryById(@PathVariable Long id) {
		Category categoryFound = new Category();
		ResponseMapper responseMapper = new ResponseMapper();
		try {
			categoryFound = categoryService.findById(id);
			responseMapper.setObject(categoryFound);
			responseMapper.setResponseCode(HttpStatus.FOUND);
			responseMapper.setMessage(HttpStatus.FOUND.getReasonPhrase());
			;
			return new ResponseEntity<ResponseMapper>(responseMapper, HttpStatus.OK);
		} catch (Exception e) {
			responseMapper.setObject(null);
			responseMapper.setResponseCode(HttpStatus.NOT_FOUND);
			responseMapper.setMessage(e.getMessage());
			;
			return new ResponseEntity<ResponseMapper>(responseMapper, HttpStatus.NOT_FOUND);
		}

	}
	@PreAuthorize("hasAuthority('ADMIN')")
	@RequestMapping(value = "/getAllCategories", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<ResponseMapper> getAllCategoriesInJsp() {
		logger.debug("entered getAllCategoriesInJsp");
		ResponseMapper responseMapper = new ResponseMapper();
		List<Category> categoriesList = new ArrayList<Category>();
		try {
			categoriesList = categoryService.findAll();
			logger.debug("size of list is " + categoriesList.size());
			responseMapper.setObject(categoriesList);
			responseMapper.setResponseCode(HttpStatus.FOUND);
			responseMapper.setMessage(HttpStatus.FOUND.getReasonPhrase());
			return new ResponseEntity<ResponseMapper>(responseMapper, HttpStatus.OK);
		} catch (Exception e) {
			responseMapper.setObject(null);
			responseMapper.setResponseCode(HttpStatus.EXPECTATION_FAILED);
			responseMapper.setMessage(e.getLocalizedMessage());
			e.printStackTrace();
			return new ResponseEntity<ResponseMapper>(responseMapper, HttpStatus.EXPECTATION_FAILED);
		}
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@RequestMapping(value = "/createCategory", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<ResponseMapper> createCategory(@RequestBody Category category) {
		ResponseMapper responseMapper = new ResponseMapper();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String role = auth.getAuthorities().toString();
		role = role.substring(1, role.length() - 1);
		System.out.println("role is "+role);
		Category categoryCreated = new Category();
		try {
			categoryCreated = categoryService.createCategory(category);
			responseMapper.setObject(categoryCreated);
			responseMapper.setResponseCode(HttpStatus.CREATED);
			responseMapper.setMessage(
					HttpStatus.CREATED.getReasonPhrase() + " with categoryId " + categoryCreated.getCategoryId());
			return new ResponseEntity<ResponseMapper>(responseMapper, HttpStatus.CREATED);

		} catch (Exception e) {
			responseMapper.setObject(null);
			responseMapper.setResponseCode(HttpStatus.EXPECTATION_FAILED);
			responseMapper.setMessage(e.getMessage());
			e.printStackTrace();
			return new ResponseEntity<ResponseMapper>(responseMapper, HttpStatus.EXPECTATION_FAILED);
		}
	}

	@RequestMapping(value = "/updateCategoryById", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<ResponseMapper> updateCategoryById(@RequestBody Category category) {
		ResponseMapper responseMapper = new ResponseMapper();
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		logger.debug("recieced updateCategoryById api call");
		Category categoryUpdate = new Category();

		try {
			category.setUpdateDateTime(timestamp);
			categoryUpdate = categoryService.updateCategoryById(category);
			responseMapper.setObject(categoryUpdate);
			responseMapper.setResponseCode(HttpStatus.OK);
			responseMapper.setMessage(HttpStatus.OK.getReasonPhrase());
			return new ResponseEntity<ResponseMapper>(responseMapper, HttpStatus.OK);
		} catch (Exception e) {
			responseMapper.setObject(null);
			responseMapper.setResponseCode(HttpStatus.EXPECTATION_FAILED);
			responseMapper.setMessage(e.getMessage());
			e.printStackTrace();
			return new ResponseEntity<ResponseMapper>(responseMapper, HttpStatus.EXPECTATION_FAILED);
		}

	}

	@RequestMapping(value = "/updateQuestionById", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<ResponseMapper> updateQuestionById(@RequestBody Questionnaire questionnaire) {
		ResponseMapper responseMapper = new ResponseMapper();
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		logger.debug("recieced updateCategoryById api call");
		Questionnaire questionnaireUpdate = new Questionnaire();
		try {
			questionnaire.setUpdateDateTime(timestamp);
			questionnaireUpdate = questionService.updateQuestionById(questionnaire);
			responseMapper.setObject(questionnaireUpdate);
			responseMapper.setResponseCode(HttpStatus.OK);
			responseMapper.setMessage(HttpStatus.OK.getReasonPhrase());
			return new ResponseEntity<ResponseMapper>(responseMapper, HttpStatus.OK);
		} catch (Exception e) {
			responseMapper.setObject(null);
			responseMapper.setResponseCode(HttpStatus.EXPECTATION_FAILED);
			responseMapper.setMessage(e.getMessage());
			e.printStackTrace();
			return new ResponseEntity<ResponseMapper>(responseMapper, HttpStatus.EXPECTATION_FAILED);
		}

	}

	@RequestMapping(value = "/deleteCategoryById/{id}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<ResponseMapper> deleteCategoryById(@PathVariable Long id) {
		ResponseMapper responseMapper = new ResponseMapper();
		logger.debug("recieced deleteCategoryById api call");
		try {
			categoryService.deleteCategory(id);
			responseMapper.setObject(null);
			responseMapper.setResponseCode(HttpStatus.OK);
			responseMapper.setMessage("Deleted SuccessFully");
			return new ResponseEntity<ResponseMapper>(responseMapper, HttpStatus.OK);
		} catch (Exception e) {

			responseMapper.setObject(null);
			responseMapper.setResponseCode(HttpStatus.EXPECTATION_FAILED);
			e.printStackTrace();
			responseMapper.setMessage(e.getMessage());
			return new ResponseEntity<ResponseMapper>(responseMapper, HttpStatus.EXPECTATION_FAILED);
		}

	}

	@RequestMapping(value = "/deleteQuestionById/{id}",method = RequestMethod.POST , produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<ResponseMapper> deleteQuestionById( @PathVariable Long id) {
		ResponseMapper responseMapper = new ResponseMapper();

		try {
			
				questionService.deleteQuestionById(id);
				responseMapper.setObject(null);
				responseMapper.setResponseCode(HttpStatus.OK);
				responseMapper.setMessage("Deleted SuccessFully");
				return new ResponseEntity<ResponseMapper>(responseMapper,HttpStatus.OK);
			
		}
		catch(Exception e)
		{
		// TODO Auto-generated catch block
			responseMapper.setObject(null);
			responseMapper.setResponseCode(HttpStatus.EXPECTATION_FAILED);
			e.printStackTrace();
			responseMapper.setMessage(e.getMessage());
			return new ResponseEntity<ResponseMapper>(responseMapper, HttpStatus.EXPECTATION_FAILED);
		}

	}

	@RequestMapping(value = "/addOrEditQuestionsByCategoryId/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<ResponseMapper> addOrEditQuestionsByCategoryId(@PathVariable Long id) 
	{
		ResponseMapper responseMapper = new ResponseMapper();
		logger.debug("recieced addOrEditQuestionsByCategoryId api call");
		try {
			List<Questionnaire> questionsFound = questionService.findByCategoryId(id);
			logger.debug("found questions of number" + questionsFound.size());
			responseMapper.setObject(questionsFound);
			responseMapper.setResponseCode(HttpStatus.FOUND);
			responseMapper.setMessage(HttpStatus.FOUND.getReasonPhrase());
			return new ResponseEntity<ResponseMapper>(responseMapper,HttpStatus.OK);
		} catch (Exception e) {
			responseMapper.setObject(null);
			responseMapper.setResponseCode(HttpStatus.EXPECTATION_FAILED);
			e.printStackTrace();
			responseMapper.setMessage(e.getMessage());
			return new ResponseEntity<ResponseMapper>(responseMapper, HttpStatus.EXPECTATION_FAILED);
		}

	}

	@RequestMapping(value = "/createQuestions",method = RequestMethod.POST , produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<ResponseMapper> createQuestions(@RequestBody Category category) throws Exception {
		ResponseMapper responseMapper = new ResponseMapper();
		try
		{
			category.setCategoryId(new Long(1));
			questionService.saveCategoryBasedQuestions(category);
			responseMapper.setObject(null);
			responseMapper.setResponseCode(HttpStatus.ACCEPTED);
			responseMapper.setMessage("saved successfully");
			return new ResponseEntity<ResponseMapper>(responseMapper,HttpStatus.ACCEPTED);
		}
		catch(DataIntegrityViolationException sqle)
		{
			responseMapper.setObject(null);
			responseMapper.setResponseCode(HttpStatus.EXPECTATION_FAILED);
			responseMapper.setMessage("passed categoryId "+sqle+" is not a valid id, please check and re enter");
			return new ResponseEntity<ResponseMapper>(responseMapper, HttpStatus.EXPECTATION_FAILED);	
		}
		catch(Exception e)
		{
			responseMapper.setObject(null);
			responseMapper.setResponseCode(HttpStatus.EXPECTATION_FAILED);
			responseMapper.setMessage(e.getLocalizedMessage());
			return new ResponseEntity<ResponseMapper>(responseMapper, HttpStatus.EXPECTATION_FAILED);	
		}
	}

}
