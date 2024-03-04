package com.woodland.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.woodland.entity.SubcategoryTypes;

public interface SubcatTypesRepo extends JpaRepository<SubcategoryTypes,Long> {
	public SubcategoryTypes findBySubCategoryType(String name);
	
	
	@Query(value="select sub_category_type from subcategory_types where sub_category_type:=name",nativeQuery=true)
	public String getEntity(String name);
	
}
