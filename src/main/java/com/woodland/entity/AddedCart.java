package com.woodland.entity;

import java.util.Date;
import java.util.List;

import com.woodland.relationEntity.AddedCartKey;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity 
@Getter
@Setter
@AllArgsConstructor(staticName="addedcartGet")
@NoArgsConstructor
public class AddedCart {
	
	@EmbeddedId
	AddedCartKey id;
	@ManyToOne
    @JoinColumn(name = "cart_id", insertable = false, updatable = false)
	Cart cart; 
    @ManyToOne
    @JoinColumn(name = "psize", insertable = false, updatable = false)
	ProductPicSizes picSizes; 
    int quantity;
    public Double Calculateprice(int quantity, Double price) {
    	return quantity*price;
    }

}
