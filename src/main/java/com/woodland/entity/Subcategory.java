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
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor(staticName="createSubcategory")
public class Subcategory {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int subCategoryId;
	private String subcategoryName;
	
	@ManyToOne
	@JoinColumn(name="category_id")
	private Category category;
	
	@OneToMany(
			cascade = CascadeType.ALL, 
			mappedBy="subCategory")
	
	private List<Products> products;
	
	@ManyToMany(cascade = {
		        CascadeType.PERSIST, 
		        CascadeType.MERGE
		    })
	@JoinTable(name="subcat_types",
	joinColumns=@JoinColumn(name="subcat_id"),
	inverseJoinColumns=@JoinColumn(name="subcat_type_id"))
	private List<SubcategoryTypes> subCategoryTypes;
	



}

