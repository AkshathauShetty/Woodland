package com.woodland.entity;

import java.util.Set;

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
public class ProductPicSizes {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long productPicSizeId;
	private String productPicSize;
	private Integer productPicSizeCount;

	@ManyToOne
	@JoinColumn(name="product_pic_id")
	private ProductPictures productPictures;
	
	
	 @OneToMany(mappedBy = "picSizes")
	 Set<AddedCart> productSizes;
	 
	 
	 @OneToMany(mappedBy = "order_productpicsize_id")
	 Set<AddedOrder> productPicSizes;
	 
	
	
}