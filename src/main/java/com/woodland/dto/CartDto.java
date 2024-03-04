package com.woodland.dto;

import java.util.Date;

import jakarta.annotation.Generated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName="cartDto")
public class CartDto {

	private Long cartId; 
	private int prodQuantity;
	private Double totalprice;
	private Integer totalItems;
	private String size;
	private ProductPicturesdto prodPicId;
	private CustomerDto customerid;
	private Double newPrice;	
	private ProductPicSizesDto prodPicSizeId;

}
