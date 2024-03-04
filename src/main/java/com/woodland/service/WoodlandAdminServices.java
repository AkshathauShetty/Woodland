package com.woodland.service;

import com.woodland.entity.Category;
import com.woodland.entity.Products;
import com.woodland.entity.Subcategory;

public interface WoodlandAdminServices {
	public String uploadImage(String file);
	public Products saveProduct(Products products);
	public Category saveCategory(Category category);
	public Subcategory savveSubcategory(Subcategory subcategory);

}
