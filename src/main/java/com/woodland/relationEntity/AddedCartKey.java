package com.woodland.relationEntity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Embeddable
@Data
@AllArgsConstructor(staticName="addedcartkeyGet")
@NoArgsConstructor
public class AddedCartKey implements Serializable {

	  @Column(name = "cart_id")
	  Long cart_id;
	  
	  @Column(name="psize")
	  private Long psize;
}
