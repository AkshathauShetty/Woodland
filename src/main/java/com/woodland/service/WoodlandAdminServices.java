package com.woodland.service;

import com.woodland.entity.Category;
import com.woodland.entity.Products;
import com.woodland.entity.Subcategory;

public interface WoodlandAdminServices {
	/**
	 * Creates a new category
	 * 
	 * @param category Category object containing the sub category and sub category types to be saved.
	 * @return		   Saved category object.
	 */
	public Category saveCategory(Category category);
	
	/**
	 * Add the new sub category to the list of sub categories.
	 *  
	 * @param subcategory
	 * @return
	 */
	public Subcategory savveSubcategory(Subcategory subcategory);

}
