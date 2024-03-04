package com.woodland.entity;

import com.woodland.relationEntity.AddedOrderKey;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Entity 
@Data
@AllArgsConstructor(staticName="addedorderGet")
@NoArgsConstructor
public class AddedOrder {
	
	@EmbeddedId
	AddedOrderKey id;

	
  	@ManyToOne
    @JoinColumn(name = "order_id", insertable = false, updatable = false)
  	Orders orders; 

    @ManyToOne
    @JoinColumn(name = "psize", insertable = false, updatable = false)
    ProductPicSizes order_productpicsize_id;


    int quantity;
    public Double Calculateprice(int quantity, Double price) {
    	return quantity*price;
    }
}