package com.woodland.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.woodland.entity.AddedCart;
import com.woodland.relationEntity.AddedCartKey;

import jakarta.persistence.Tuple;

import java.util.List;


@Repository
public interface AddedCartRepo extends JpaRepository<AddedCart, AddedCartKey> {
	
	@Query(value="select SUM(quantity)\r\n"
			+ "from added_cart \r\n"
			+ "where cart_id = :cartId\r\n"
			+ "group by(cart_id)",nativeQuery=true)
	Integer getTotalItems(@Param("cartId") Long cartid);
	
	@Query(value="select pps.product_pic_id,pic.product_pic_url,pic.product_color,pic.product_pic_name,ac.quantity,pps.product_pic_size_count,p.product_price,p.product_offers,pps.product_pic_size,pps.product_pic_size_id\r\n"
			+ "from added_cart ac join product_pic_sizes pps on ac.psize = pps.product_pic_size_id\r\n"
			+ "join product_pictures pic on pps.product_pic_id=pic.product_pic_id\r\n"
			+ "join products p on pic.product_id=p.product_id\r\n"
			+ "where ac.cart_id=:cartId",nativeQuery=true)
	List<Tuple>  getcartDetails(@Param("cartId") Long cartId);
	
	@Modifying
	@Query(value="delete from added_cart where psize=:picsizeId  and cart_id=:cartId",nativeQuery=true)
	void  deleteProduct(@Param("cartId") Long cartId,@Param("picsizeId") Long picsizeId);
	
	@Modifying
	@Query(value="update added_cart set quantity=:qty where psize=:picsizeId and cart_id=:cartId",nativeQuery=true)
	void updateProduct(@Param("cartId") Long cartId,@Param("picsizeId") Long picsizeIdize,@Param("qty") Integer qty);

	@Modifying
	@Query(value="delete from added_cart where cart_id=:cart_id",nativeQuery=true)
	void deleteAddedcartByCartId(@Param("cart_id") Long cart_id);
}
