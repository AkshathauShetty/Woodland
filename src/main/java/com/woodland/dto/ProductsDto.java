package com.woodland.dto;

import java.util.Date;
import java.util.List;

import com.woodland.entity.ProductPictures;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName="create")
public class ProductsDto {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long productdtoId;
	private Double productdtoPrice;
	private String suitabledtoFor;
	private Integer productdtoOffers;
	private Double productFinalPrice;
	private String bestSeller;
	private List<ProductPicturesdto> productPicturesdto;
	private SubcategoryDto subcategoryDto;
	private SubcategoryTypesDto subcategoryTypesDto;
	

}
