package com.reactive.api;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.reactive.api.controller.ProductController;
import com.reactive.api.dto.ProductDto;
import com.reactive.api.service.ProductService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@RunWith(SpringRunner.class)
@WebFluxTest(ProductController.class)
class ReactiveProgrammingApplicationTests {

	@Autowired
	private WebTestClient webTestClient;

	@MockBean
	private ProductService service;

	@Test
	public void addProductTest() {
		Mono<ProductDto> productDtoMono = Mono.just(new ProductDto("102", "Mobile", 5, 10000));
		when(service.saveProduct(productDtoMono)).thenReturn(productDtoMono);;
		webTestClient.post().uri("/products")	
					 .body(Mono.just(productDtoMono), ProductDto.class)
					 .exchange()
					 .expectStatus()
					 .isOk();

	}
	
	@Test
	public void getProductsTest(){
		Flux<ProductDto> productDtoFlux=Flux.just(new ProductDto("102","mobile",1,10000),
												  new ProductDto("103","TV",1,50000));
		when(service.getProducts()).thenReturn(productDtoFlux);
		Flux<ProductDto> responseBody = webTestClient.get().uri("/products")
														.exchange()
														.expectStatus().isOk()
														.returnResult(ProductDto.class)
														.getResponseBody();

		StepVerifier.create(responseBody)
				.expectSubscription()
				.expectNext(new ProductDto("102","mobile",1,10000))
				.expectNext(new ProductDto("103","TV",1,50000))
				.verifyComplete();

	}
	
	@Test
	public void getProductTest() {
		Mono<ProductDto> product = Mono.just(new ProductDto("102", "Mobile", 5, 10000));
		when(service.getProduct(any())).thenReturn(product);
		
		Flux<ProductDto> responseBody = webTestClient.get().uri("/products/102")
												.exchange()
												.expectStatus().isOk()
												.returnResult(ProductDto.class)
												.getResponseBody();
		
		StepVerifier.create(responseBody)
		.expectSubscription()
		.expectNextMatches(p->p.getName().equalsIgnoreCase("Mobile"))
		.verifyComplete();

	}
	
	@Test
	public void updateProductTest(){
		Mono<ProductDto> productDtoMono=Mono.just(new ProductDto("102","mobile",1,10000));
		when(service.updateProduct(productDtoMono,"102")).thenReturn(productDtoMono);

		webTestClient.put().uri("/products/update/102")
				.body(Mono.just(productDtoMono),ProductDto.class)
				.exchange()
				.expectStatus().isOk();//200
	}

	@Test
	public void deleteProductTest(){
    	given(service.deleteProduct(any())).willReturn(Mono.empty());
		webTestClient.delete().uri("/products/delete/102")
				.exchange()
				.expectStatus().isOk();//200
	}

}
