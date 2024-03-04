package com.woodland.serviceImplementation;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.woodland.dto.CartDto;
import com.woodland.dto.CartTotal;
import com.woodland.dto.CustomerDto;
import com.woodland.entity.AddedCart;
import com.woodland.entity.Cart;
import com.woodland.entity.Customer;
import com.woodland.entity.ProductPicSizes;
import com.woodland.entity.ProductPictures;
import com.woodland.entity.Products;
import com.woodland.exception.DataNotFound;
import com.woodland.exception.InputInvalid;
import com.woodland.relationEntity.AddedCartKey;
import com.woodland.repository.AddedCartRepo;
import com.woodland.repository.CartRepo;
import com.woodland.repository.CategoryRepo;
import com.woodland.repository.CustomerRepo;
import com.woodland.repository.ProductImageRepo;
import com.woodland.repository.ProductPicSizesRepo;
import com.woodland.repository.ProductsRepo;
import com.woodland.repository.SubCategoryRepo;
import com.woodland.repository.SubcatTypesRepo;
import com.woodland.service.WoodlandCartServices;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Tuple;


@Service
public class WoodlandCartServiceImpl implements WoodlandCartServices{
	
	@Autowired
	private CustomerRepo customerRepo;
	@Autowired
	private AddedCartRepo addedCartRepo;
	@Autowired
	private CartRepo  cartRepo;
	@Autowired
	private ProductPicSizesRepo picSizesRepo;
	
	public Double calculateFinalprice(Double productdtoPrice, Integer productdtoOffers) {
		return productdtoPrice-(productdtoPrice*productdtoOffers/100);
	}
	
	/** 
	 * saves the cart item to the user's cart list 
	 * @param {CartDto} cartdto - user cartdto to be saved
	 * @return CartDto - saved cartdto 
	 */
	@Override
	public CartDto addTocart(Principal principal,CartDto cartdto) {
		if(cartdto!=null && principal!=null && !principal.getName().isEmpty()) {
    		String phone =principal.getName();
    		CustomerDto customerdto = new CustomerDto();
    		customerdto.setPhone(phone);
    		cartdto.setCustomerid(customerdto);
			Customer customer = customerRepo.findByPhone(cartdto.getCustomerid().getPhone());
			if(customer!=null) {
				//ProductPictures productPic = productImageRepo.findById(cartdto.getProdPicId().getProductPicId()).orElse(null);
				ProductPicSizes picSizes = picSizesRepo.findById(cartdto.getProdPicSizeId().getProductPicSizeId()).orElse(null);
				Optional<Cart> oldcart = cartRepo.findByCustomerCarts(customer.getCustomer_id());
				Cart cart=null;
				// create a new cart if the cart is not present
				if(oldcart!=null) {
					if(oldcart.isEmpty()) {
						cart = Cart.cartGet(0l, 0d, 0, customer,null);
						cartRepo.save(cart);
					}
					else{
						cart=oldcart.get();		
					}
					AddedCartKey addedcartKey = AddedCartKey.addedcartkeyGet( cart.getCartId(),picSizes.getProductPicSizeId());
					AddedCart addedCart = AddedCart.addedcartGet(addedcartKey, cart, picSizes,cartdto.getProdQuantity());
					addedCartRepo.save(addedCart);
					Cart updatedCart = cartRepo.findByCustomerCarts(customer.getCustomer_id()).get();		
					cartdto.setCartId(cart.getCartId());
					cartdto.setTotalItems(updatedCart.getCartTotalItems());
					cartdto.setTotalprice(updatedCart.getCartTotalAmount());
					return cartdto;
				}
				else {
					throw new DataNotFound("Error occcured!");
				}
			}
			else {
				throw new DataNotFound("Login again!");
			}
		}
		else {
			throw new InputInvalid("Input not valid");
		}
		
	}
	
	/** 
	 *  Returns the cart details of the user using user cartId
	 * @param {Long} cartId - cartid of the user
	 * @return  {List<CartTotal>} - list of cart items associated with the cartId 
	 */
	@Override
	public List<CartTotal> getCart(Long cartId) {
		List<Tuple> cartDetails = addedCartRepo.getcartDetails(cartId);
		if(cartDetails!=null && !cartDetails.isEmpty()) {
			List<CartTotal> cartTotalDto =
					cartDetails.stream()
					.map(item->
					CartTotal.cartTotalDto(0l,
							item.get(0,Long.class),
							item.get(1,String.class),
							item.get(2,String.class), 
							item.get(3,String.class),
							item.get(4,Integer.class),
							item.get(5,Integer.class),
							item.get(6,Double.class),
							item.get(7,Integer.class),
							item.get(8,String.class),
							item.get(9,Long.class),
							0d
							))
					.collect(Collectors.toList());
					
					for(CartTotal ct : cartTotalDto) {
						ct.setProductFinalPrice(calculateFinalprice(ct.getProductPrice(), ct.getProductOffers()));
					}
					return cartTotalDto;
		}
		else {
			throw new DataNotFound("No items in the cart! add items to your cart");
		}
	}

	/**
	 * returns the cart id associated with the user
	 * @param {String} phone
	 * @return {Long} -  if the cart is present return associated cart id , or else return 0
	 */
	@Override
	public Long getCartId(Principal principal) {
		if(principal!=null && principal.getName()!=null) {
			String phone = principal.getName();
			Customer customer = customerRepo.findByPhone(phone);
			if(customer!=null) {
				Optional<Cart> cart = cartRepo.findByCustomerId(customer.getCustomer_id());
				if(cart!=null && cart.isEmpty()) {
					return 0l;
				}
				else {
					return cart.get().getCartId();
				}
			}
			else {
				throw new DataNotFound("Error occured login again");
			}
		}
		else {
			throw new InputInvalid("Invalid input");
		}
	}


	/**
	 * delete the product from the cart based on the primary key details provided in the cartDto
	 * @param {CartDto} cartdto
	 * @return {String}  
	 */
	@Transactional
	@Override
	public String deleteCart(CartDto cartdto) {
		if(cartdto!=null) {
			addedCartRepo.deleteProduct(cartdto.getCartId(),cartdto.getProdPicSizeId().getProductPicSizeId());
			return "deleted successfully";
		}
		else {
			throw new InputInvalid("Invalid input");
		}
	}


	/** 
	 * Updates the cart item quantity based on the keys: cartId and ProductPicturesSizeId
	 * @param {List<CartDto>} cart - List of CartDto 
	 * @return { List<CartDto>} - List of CartDto 
	 */
	@Transactional
	@Override
	public String updateCart(List<CartDto> cart) {
		if(cart!=null) {
			for(CartDto cartItems : cart ) {
				addedCartRepo.updateProduct(cartItems.getCartId(), cartItems.getProdPicSizeId().getProductPicSizeId(), cartItems.getProdQuantity());
			}
			return "updated successfulyy";
		}
		else {
			throw new InputInvalid("Input invalid");
		}
	}

	@Transactional
	@Override
	public String deleteAddedCart(Principal principal) {
		if(principal!=null) {
			String phone = principal.getName();
			Customer customer = customerRepo.findByPhone(phone);
			if(customer!=null) {
				Long customerId = customer.getCustomer_id();
				Long cartId = cartRepo.getCartIdByCustomerID(customerId);
				addedCartRepo.deleteAddedcartByCartId(cartId);
				return "cart Deleted successfully";
			}
			else {
				throw new DataNotFound("No customer found!");
			}
		}
		else {
			throw new InputInvalid("Invalid input");
		}

	}
}
