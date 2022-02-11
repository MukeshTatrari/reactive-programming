package com.reactive.api.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.reactive.api.dao.CustomerDao;
import com.reactive.api.dto.Customer;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Configuration
public class CustomerStreamHandler {

	@Autowired
	private CustomerDao customerDao;

	public Mono<ServerResponse> getCustomers(ServerRequest request) {

		Flux<Customer> customerList = customerDao.getcustomersStream();
		return ServerResponse.ok()
				.contentType(MediaType.TEXT_EVENT_STREAM)
				.body(customerList, Customer.class);

	}

}
