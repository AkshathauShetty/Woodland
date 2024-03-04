package com.woodland.serviceImplementation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.woodland.entity.Category;
import com.woodland.entity.Products;
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
	
	@Override
	public String uploadImage(String file) {
		return null;
	}
	@Override
	public Products saveProduct(Products products) {
		return null;
	}

	@Override
	public Category saveCategory(Category category) {
		Category cats = Category.createCategory(category.getCategoryId(),category.getCategoryName(),null);
		Category catold =	categoryRepo.findByCategoryName(cats.getCategoryName());
		List<Subcategory> scat = category.getSubCategory();		
		for(Subcategory scats : scat) {
				List<SubcategoryTypes> scatTypes =  scats.getSubCategoryTypes();
				if(scatTypes!=null){
					List<SubcategoryTypes> scatTypesnew = new ArrayList<>();
					for(SubcategoryTypes subtypes: scatTypes) {
						SubcategoryTypes scatTypeOld =  subcatTypesRepo.findBySubCategoryType(subtypes.getSubCategoryType());
						if(scatTypeOld!=null) {
							scatTypeOld.getSubCategory().add(scats);
							scatTypesnew.add(scatTypeOld);
						}
						else {
							List<Subcategory> scatList = new ArrayList<>();
							scatList.add(scats);
							subtypes.setSubCategory(scatList);
							scatTypesnew.add(subtypes);
						}
					}
					scats.setSubCategoryTypes(scatTypesnew);
				}	
			if(catold!=null) {
				scats.setCategory(catold);
				catold.getSubCategory().add(scats);
			}else {
				scats.setCategory(cats);
			}
		}	
		
		if(catold==null) {
		cats.setSubCategory(scat);
		Category cat = categoryRepo.save(cats);
		}
		else {
			Category cat = categoryRepo.save(catold);
		}
	
		return null;
}


	@Override
	public Subcategory savveSubcategory(Subcategory subcategory) {
		Subcategory scat = subcategoryRepo.save(subcategory);
		return scat;
	}


}
