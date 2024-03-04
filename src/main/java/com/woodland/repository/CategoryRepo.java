package com.woodland.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.woodland.entity.Category;
import com.woodland.entity.ProductPictures;
import com.woodland.entity.Subcategory;


@Repository
public interface CategoryRepo  extends JpaRepository<Category, Long> {
	
	public Category findByCategoryName(String name);
	
	@Query(value="Select category_id,category_name from category",nativeQuery=true)
	public List getAllCategories();
	

}
