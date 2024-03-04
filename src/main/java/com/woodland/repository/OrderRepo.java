package com.woodland.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.woodland.entity.AddedCart;
import com.woodland.entity.Cart;
import com.woodland.entity.Orders;

import jakarta.persistence.Tuple;


@Repository
public interface OrderRepo  extends JpaRepository<Orders, AddedCart> { 
	
	@Query(value="select * from orders where order_id = :order_id",nativeQuery=true)
	Orders findOrdersByOrderId(@Param("order_id") Long order_id);
	

	@Query(value="select * from orders where customer_id = :customer_id",nativeQuery=true)
	List<Orders> findOrdersByCusstomerId(@Param("customer_id") Long customer_id);
	
	
	@Query(value="select pps.product_pic_id,pic.product_pic_url,pic.product_color,pic.product_pic_name,ac.quantity,pps.product_pic_size_count,p.product_price,p.product_offers,pps.product_pic_size,pps.product_pic_size_id,o.order_date,o.order_id\r\n"
			+ "from added_order ac join product_pic_sizes pps on ac.psize = pps.product_pic_size_id\r\n"
			+ "join product_pictures pic on pps.product_pic_id=pic.product_pic_id\r\n"
			+ "join products p on pic.product_id=p.product_id\r\n"
			+ "join orders o on o.order_id=ac.order_id\r\n"
			+ "where ac.order_id=:orderId "
			+ "and  Datediff(DAY,o.order_date,(select getDate() as 'currentDate'))>=7;",nativeQuery=true)
	List<Tuple>  getorderDetails(@Param("orderId") Long orderId);
	
	@Query(value="select pps.product_pic_id,pic.product_pic_url,pic.product_color,pic.product_pic_name,ac.quantity,pps.product_pic_size_count,p.product_price,p.product_offers,pps.product_pic_size,pps.product_pic_size_id,o.order_date,o.order_id\r\n"
			+ "from added_order ac join product_pic_sizes pps on ac.psize = pps.product_pic_size_id\r\n"
			+ "join product_pictures pic on pps.product_pic_id=pic.product_pic_id\r\n"
			+ "join products p on pic.product_id=p.product_id\r\n"
			+ "join orders o on o.order_id=ac.order_id\r\n"
			+ "where ac.order_id=:orderId "
			+ "and  Datediff(DAY,o.order_date,(select getDate() as 'currentDate'))<7;",nativeQuery=true)
	List<Tuple>  getRecentorderDetails(@Param("orderId") Long orderId);

}
