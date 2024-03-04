package com.woodland.service;

import java.security.Principal;
import java.util.List;

import com.woodland.dto.CartDto;
public interface WoodlandCartServices {
	public CartDto addTocart(Principal principal,CartDto cartdto);
	public List getCart(Long cartId);
	public String deleteCart(CartDto cart);
	public String  updateCart(List<CartDto> cart);
	public Long getCartId(Principal principal);
	public String deleteAddedCart(Principal principal);

}
