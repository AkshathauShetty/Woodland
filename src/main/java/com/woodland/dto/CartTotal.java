package com.woodland.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor(staticName="cartTotalDto")
public class CartTotal {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long cartItemId;
	private Long productPicId;
	private String productpicUrl;
	private String productColor;
	private String productName;
	private Integer productQuantity;
	private Integer productOriginalQuantity;
	private Double productPrice; 
	private Integer productOffers;
	private String productSize;
	private Long productPicSizeId;
	private Double productFinalPrice;


}