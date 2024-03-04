package com.woodland.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@NoArgsConstructor
@AllArgsConstructor(staticName="getPdPicdto")
public class ProductPicSizesDto {
		@Id
		@GeneratedValue(strategy=GenerationType.IDENTITY)
		private Long productPicSizeId;
		private String productPicSize;
		private Integer productPicSizeCount;
		

}