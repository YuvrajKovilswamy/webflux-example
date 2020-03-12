package com.zorders.fos.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.zorders.fos.model.Restaurants;
@Repository
public interface OrderRepository extends MongoRepository<Restaurants, String>{

}
