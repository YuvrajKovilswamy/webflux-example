package com.zorders.fos.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.zorders.fos.model.OrderDetails;
import com.zorders.fos.model.Restaurants;
import com.zorders.fos.repository.OrderRepository;

import reactor.core.publisher.Flux;

@Service
public class OrderService {

	@Autowired
	OrderRepository orderRepository;
	@Autowired
	ReactiveMongoOperations reactiveMongoOperations;

	public List<Restaurants> getRestaurantDetailsByName(String name) {
		Flux<Restaurants> restaurantsDetails = null;
		if (StringUtils.isEmpty(restaurantsDetails)) {
			// restaurantsDetails = orderRepository.findAll();
			restaurantsDetails = reactiveMongoOperations.findAll(Restaurants.class);
		}
		return restaurantsDetails.toStream().filter(x -> x.getRestaurantName().equalsIgnoreCase(name))
				.collect(Collectors.toList());
	}

	public Flux<Restaurants> getDetailsOfAllRestaurants() {
		return reactiveMongoOperations.findAll(Restaurants.class);
	}

	public OrderDetails calculateOrder(OrderDetails orderDetails) {
		List<Restaurants> orderedRestaurants =

				reactiveMongoOperations.findAll(Restaurants.class).toStream()
						.filter(data -> data.getRestaurantName().equalsIgnoreCase(orderDetails.getRestaurantName()))
						.collect(Collectors.toList());

		for (Restaurants restaurant : orderedRestaurants) {
			orderDetails.setPrice(restaurant.getPrice() * orderDetails.getQuantity());
		}

		return orderDetails;
	}
}
