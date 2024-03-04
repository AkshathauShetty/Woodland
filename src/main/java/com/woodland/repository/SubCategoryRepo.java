package com.woodland.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.woodland.entity.ProductPictures;
import com.woodland.entity.Subcategory;
import com.woodland.entity.SubcategoryTypes;
import jakarta.persistence.Tuple;

@Repository
public interface SubCategoryRepo extends JpaRepository<Subcategory, Long> {
	public Subcategory findBySubcategoryName(String name);
	
	@Query(value="select c.category_name, sc.sub_category_id, sc.subcategory_name\r\n"
			+ "from category c join subcategory sc on c.category_id=sc.category_id\r\n"
			+ "where sc.sub_category_id in(\r\n"
			+ "select sub_category_id from products where suitable_for=:specFor)"
			+ "and c.category_name=:cat_name",nativeQuery=true)
	List getCategories(@Param("specFor")String specificFor);
	
	


} 
