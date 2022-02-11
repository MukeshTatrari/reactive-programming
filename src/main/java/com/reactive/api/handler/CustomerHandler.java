package com.reactive.api.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.reactive.api.dao.CustomerDao;
import com.reactive.api.dto.Customer;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CustomerHandler {

	@Autowired
	private CustomerDao customerDao;

	public Mono<ServerResponse> loadCustomers(ServerRequest request) {

		Flux<Customer> customerList = customerDao.getcustomerList();
		return ServerResponse.ok().body(customerList, Customer.class);

	}

	public Mono<ServerResponse> findCustomerById(ServerRequest request) {

		int customerId = Integer.valueOf(request.pathVariable("input"));
//		Mono<Customer> customerList = customerDao.getcustomerList().filter(c->c.getId()==customerId).take(1).single();
		Mono<Customer> customer = customerDao.getcustomerList().filter(c -> c.getId() == customerId).next();
		return ServerResponse.ok().body(customer, Customer.class);

	}

	public Mono<ServerResponse> saveCustomer(ServerRequest request) {

		Mono<Customer> customerMono = request.bodyToMono(Customer.class);
		Mono<String> response = customerMono.map(dto -> dto.getId() + ":" + dto.getName());
		return ServerResponse.ok().body(customerMono, Customer.class);

	}

}
