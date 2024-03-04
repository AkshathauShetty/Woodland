package com.woodland.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.woodland.entity.AddedOrder;
import com.woodland.relationEntity.AddedOrderKey;

import jakarta.persistence.Tuple;


@Repository
public interface AddedOrderRepo  extends JpaRepository<AddedOrder, AddedOrderKey> {

	

}
