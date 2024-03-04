package com.woodland.relationEntity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Embeddable
@Data
@AllArgsConstructor(staticName="addedorderkeyGet")
@NoArgsConstructor
public class AddedOrderKey implements Serializable {

	  @Column(name = "order_id")
	  Long order_id;
	  
	  @Column(name="psize")
	  private Long psize;
}

