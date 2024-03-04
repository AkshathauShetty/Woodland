package com.woodland.entity;

import java.util.Date;
import java.util.List;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Products {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long productId;
	private Double productPrice;
	private String suitableFor;
	private Integer productOffers;
	private String bestSeller;

	
	@OneToMany(
			cascade = CascadeType.ALL, 
			mappedBy="product")
	
	private List<ProductPictures> productPictures;
	
	@ManyToOne
	@JoinColumn(name="sub_category_id")
	private Subcategory subCategory;
	
	@ManyToOne
	@JoinColumn(name="sub_categorytype_id")
	private SubcategoryTypes subCategorytype;
	

}
