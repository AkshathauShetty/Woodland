package com.woodland.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor(staticName="createSubcattypes")
public class SubcategoryTypes {
		
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int subCategoryTypeId;
	private String subCategoryType;

	@ManyToMany(mappedBy="subCategoryTypes")
	private List<Subcategory> subCategory;
	
	@OneToMany(
			cascade = CascadeType.ALL, 
			mappedBy="subCategorytype")	
	private List<Products> products;

}
