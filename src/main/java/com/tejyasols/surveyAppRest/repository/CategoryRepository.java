package com.tejyasols.surveyAppRest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tejyasols.surveyAppRest.entity.Category;


public interface CategoryRepository extends JpaRepository<Category, Long> {

}
