package com.woodland.entity;


import java.util.List;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName="createProductPicturesdto")
@Entity
public class ProductPictures {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long productPicId;
	private String productColor;
	private String productPicUrl;
	private String productPicName;
	@ManyToOne
	@JoinColumn(name="product_id")
	private Products product;

	@OneToMany(
			cascade = CascadeType.ALL, 
			mappedBy="productPictures")
	private List<ProductPicSizes> productPictureSizes;
	

	

}
