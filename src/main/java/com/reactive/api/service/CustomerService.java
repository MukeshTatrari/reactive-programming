package com.reactive.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reactive.api.dao.CustomerDao;
import com.reactive.api.dto.Customer;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Service
@Slf4j
public class CustomerService {

	@Autowired
	private CustomerDao customerDao;

	public List<Customer> loadAllCustomers() {

		long start = System.currentTimeMillis();
		List<Customer> customers = customerDao.getcustomers();
		long end = System.currentTimeMillis();
		log.info("Total Execution Time :::" + (end - start));
		return customers;
	}

	public Flux<Customer> loadAllCustomersStream() {

		long start = System.currentTimeMillis();
		Flux<Customer> customers = customerDao.getcustomersStream();
		long end = System.currentTimeMillis();
		log.info("Total Execution Time :::" + (end - start));
		return customers;
	}

}
