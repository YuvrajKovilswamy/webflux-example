package com.zorders.fos.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.zorders.fos.model.OrderDetails;
import com.zorders.fos.model.Restaurants;
import com.zorders.fos.service.OrderService;

import reactor.core.publisher.Flux;

@RestController
@RequestMapping(value = "/zorders")
public class OrderController {

	Logger logger = Logger.getLogger(OrderController.class);

	@Autowired
	private OrderService orderService;

	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public ResponseEntity<Flux<Restaurants>> getAllRestauntDetails() {
		return new ResponseEntity<Flux<Restaurants>>(orderService.getDetailsOfAllRestaurants(), HttpStatus.OK);

	}

	@RequestMapping(path = "/{name}", method = RequestMethod.GET)
	public ResponseEntity<List<Restaurants>> getRestauntDetailsByName(@PathVariable("name") String name) {
		if (name != null) {
			return new ResponseEntity<List<Restaurants>>(orderService.getRestaurantDetailsByName(name), HttpStatus.OK);
		}
		return new ResponseEntity<List<Restaurants>>(HttpStatus.BAD_REQUEST);

	}

	@RequestMapping(value = "/ordered", method = RequestMethod.POST, consumes = "application/json")
	public ResponseEntity<String> orderDetails(@RequestBody OrderDetails orderDetails) {
		String message = "Your order has been accepted by " + orderDetails.getRestaurantName().toUpperCase()
				+ ".\nYou will be charged an amount of Rs." + orderService.calculateOrder(orderDetails).getPrice();

		return new ResponseEntity<String>(message, HttpStatus.ACCEPTED);
	}
}
