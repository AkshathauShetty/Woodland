package com.woodland.dto;

import java.util.Date;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor(staticName="creatreOrderHistoryDto")
public class OrderHistoryDto {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long orderId;
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
	private Date orderedDate;
	private String orderStatus;
	
}
