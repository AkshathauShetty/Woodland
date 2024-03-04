package com.woodland.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.woodland.entity.Products;


@Repository
public interface ProductsRepo  extends JpaRepository<Products, Long> {
	
	/*
	 * Note : while passing the parameter just pass it without quotes 
	 * 	 */
	@Query(value="select * from products where sub_category_id in ( select sub_category_id from subcategory where subcategory_name like %:scat_name%)",nativeQuery=true)	
	List<Products> browseProductsBySubcats(@Param("scat_name") String subcategory_name);

	@Query(value="select * from products where sub_category_id in ( select sub_category_id from subcategory where subcategory_name=?1)",nativeQuery=true)
	List<Products> browseProductsBySubcat(String subcategory_name);
	
	@Query(value="select * from products where suitable_for=:specFor or suitable_for='Both'",nativeQuery=true)
	List<Products> getProds(@Param("specFor")String specificFor);
	
	
	@Query(value = "select DISTINCT(product_pic_size) \r\n"
			+ "from product_pic_sizes \r\n"
			+ "where product_pic_id= :prod_pid", nativeQuery = true)
	public List<String> getAllSizes(@Param("prod_pid") Long product_pic_id);

	@Query(value = "select * from products where sub_category_id in(select sub_category_id from subcategory where subcategory_name=:scat_name)\r\n"
			+ "	and sub_categorytype_id in(select sub_category_type_id from subcategory_types where  sub_category_type=:scat_type_name)"
			+ " and (suitable_for=:specific_for or suitable_for='Both')",nativeQuery = true)
	public List<Products> getProductonFilter(@Param("scat_name") String subCategory,@Param("scat_type_name") String subCategoryType,@Param("specific_for")String speficiFor);

	@Query(value = "select * from products where sub_category_id in(select sub_category_id from subcategory where subcategory_name=:scat_name)\r\n"
			+ " and (suitable_for=:specific_for or suitable_for='Both')",nativeQuery = true)
	public List<Products> getProductonScatFilter(@Param("scat_name") String subCategory,@Param("specific_for")String speficiFor);

	@Query(value = "select * from products where sub_category_id in(select sub_category_id from subcategory where subcategory_name=:scat_name)\r\n"
			+ "	and sub_categorytype_id in(select sub_category_type_id from subcategory_types where  sub_category_type=:scat_type_name)",nativeQuery = true)
	public List<Products> getProductonCatscatFilter(@Param("scat_name") String subCategory,@Param("scat_type_name") String subCategoryType);


	@Query(value = "select * from products where sub_category_id in (Select sub_category_id from subcategory where category_id in (select category_id from category where category_name=:cat_name))\r\n"
	+ " and (suitable_for=:specific_for or suitable_for='Both')",nativeQuery = true)
	public List<Products> getProductByCategory(@Param("cat_name") String categoryName,@Param("specific_for")String speficiFor);

	
} 
