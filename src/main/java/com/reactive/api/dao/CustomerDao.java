package com.reactive.api.dao;
import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.stereotype.Repository;

import com.reactive.api.dto.Customer;

import reactor.core.publisher.Flux;

@Repository
public class CustomerDao {

	public List<Customer> getcustomers() {
		return IntStream.rangeClosed(0, 10)
					.peek(i->System.out.println("Processing count ::"+i))
					.peek(CustomerDao::sleepExection)
					.mapToObj(i -> new Customer(i, "customer" + i))
					.collect(Collectors.toList());
	}
	
	
	public Flux<Customer> getcustomersStream() {
		return Flux.range(0, 10)
					.doOnNext(i->System.out.println("Processing count in Stream ::"+i))
					.delayElements(Duration.ofSeconds(1))
					.map(i -> new Customer(i, "customer" + i));
	}
	
	public Flux<Customer> getcustomerList() {
		return Flux.range(0, 50)
					.doOnNext(i->System.out.println("Processing count in Stream ::"+i))
					.map(i -> new Customer(i, "customer" + i));
	}
	
	private static void sleepExection(int i) {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
