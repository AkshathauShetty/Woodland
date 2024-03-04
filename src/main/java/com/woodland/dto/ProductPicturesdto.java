package com.woodland.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.List;

import com.woodland.entity.ProductPictures;
import com.woodland.entity.Products;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName="createProductPic")
public class ProductPicturesdto {	
		@Id
		@GeneratedValue(strategy=GenerationType.IDENTITY)
		private Long productPicId;
		private String productColor;
		private String productPicUrl;
		private String productPicName;
		private List<ProductPicSizesDto> productPicSizesdto;
		private List<String> productSizes;
		

}
