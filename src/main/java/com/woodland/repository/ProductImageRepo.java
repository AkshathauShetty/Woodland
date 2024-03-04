package com.woodland.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.woodland.entity.ProductPictures;


@Repository
public interface ProductImageRepo extends JpaRepository<ProductPictures, Long> {
	

}
