package com.tejyasols.surveyAppRest.service;

import java.util.List;

import com.tejyasols.surveyAppRest.entity.Category;


public interface CategoryService {
	
	public Category createCategory(Category category) throws Exception;
	
	public List<Category> findAll() throws Exception;
	
	public Category findById(Long id) throws Exception;
	
	public Category updateCategoryById(Category category) throws Exception;
	
	public void deleteCategory(Long id) throws Exception;
	
}
