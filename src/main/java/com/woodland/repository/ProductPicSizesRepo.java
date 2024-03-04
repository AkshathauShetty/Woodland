package com.woodland.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.woodland.entity.ProductPicSizes;


@Repository
public interface ProductPicSizesRepo extends JpaRepository<ProductPicSizes, Long> {

}
