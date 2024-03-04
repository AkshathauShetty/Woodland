package com.woodland.serviceImplementation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.woodland.entity.Category;
import com.woodland.entity.Subcategory;
import com.woodland.entity.SubcategoryTypes;
import com.woodland.repository.CategoryRepo;
import com.woodland.repository.SubCategoryRepo;
import com.woodland.repository.SubcatTypesRepo;
import com.woodland.service.WoodlandAdminServices;
@Service
public class WoodlandAdminServiceImpl implements WoodlandAdminServices{

	@Autowired
	private CategoryRepo categoryRepo;	
	@Autowired
	private SubCategoryRepo subcategoryRepo;;	
	@Autowired
	private SubcatTypesRepo subcatTypesRepo;
	

	/**
	 * Creates a new Category.
	 * Save the category along with its sub category and sub category types.
	 * 
	 * @param category Category object containing the sub category and sub category types to be saved.
	 * @return		   Saved category object.
	 */
	@Override
	public Category saveCategory(Category category) {
		Category categoryNew = Category.createCategory(category.getCategoryId(),category.getCategoryName(),null);
		Category categoryOld =	categoryRepo.findByCategoryName(categoryNew.getCategoryName());
		List<Subcategory> subCategoryList = category.getSubCategory();		
		for(Subcategory subCategory : subCategoryList) {
				List<SubcategoryTypes> subCategoryTypes =  subCategory.getSubCategoryTypes();
				if(subCategoryTypes!=null){
					List<SubcategoryTypes> subcategoryTypeListNew = new ArrayList<>();
					for(SubcategoryTypes subCategoryType: subCategoryTypes) {
						SubcategoryTypes subCatgoryTypeListOld =  subcatTypesRepo.findBySubCategoryType(subCategoryType.getSubCategoryType());
						if(subCatgoryTypeListOld!=null) {
							subCatgoryTypeListOld.getSubCategory().add(subCategory);
							subcategoryTypeListNew.add(subCatgoryTypeListOld);
						}
						else {
							List<Subcategory> subCategoryListHolder = new ArrayList<>();
							subCategoryListHolder.add(subCategory);
							subCategoryType.setSubCategory(subCategoryListHolder);
							subcategoryTypeListNew.add(subCategoryType);
						}
					}
					subCategory.setSubCategoryTypes(subcategoryTypeListNew);
				}	
			if(categoryOld!=null) {
				subCategory.setCategory(categoryOld);
				categoryOld.getSubCategory().add(subCategory);
			}else {
				subCategory.setCategory(categoryNew);
			}
		}	
		
		if(categoryOld==null) {
			categoryNew.setSubCategory(subCategoryList);
			Category categorySaved = categoryRepo.save(categoryNew);
			return categorySaved;
		}
		else {
			Category categorySaved = categoryRepo.save(categoryOld);
			return categorySaved;
		}	
	}

	/**
	 * Add the new sub category to the list of sub categories.
	 * 
	 * @param subcategory sub category to be saved 
	 * @return			  Returns the saved sub category.
	 */
	@Override
	public Subcategory savveSubcategory(Subcategory subcategory) {
		Subcategory subCategorySaved = subcategoryRepo.save(subcategory);
		return subCategorySaved;
	}


}
