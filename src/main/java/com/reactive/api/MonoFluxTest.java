package com.reactive.api;

import org.junit.jupiter.api.Test;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class MonoFluxTest {

	@Test
	public void testMono() {
		Mono<?> test = Mono.just("SYSM").then(Mono.error(new RuntimeException("Exception Ocuured"))).log();
		test.subscribe(System.out::println, e -> System.err.println(e.getMessage()));
	}

	@Test
	public void TestFlux() {
		Flux<?> test = Flux.just("Spring", "Mukesh", "Hibernate", "MS");
		test.subscribe(System.out::println);
	}

}
