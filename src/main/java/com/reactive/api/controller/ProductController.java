package com.reactive.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.reactive.api.dto.ProductDto;
import com.reactive.api.service.ProductService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/products")
public class ProductController {

	@Autowired
	private ProductService service;

	@GetMapping
	public Flux<ProductDto> products() {
		return service.getProducts();
	}

	@GetMapping("/{id}")
	public Mono<ProductDto> getProduct(@PathVariable String id) {
		return service.getProduct(id);
	}

	@GetMapping("/product-range")
	public Flux<ProductDto> getProductInRange(@RequestParam("min") double min, @RequestParam("max") double max) {
		return service.getProductInRange(min, max);
	}

	@PostMapping
	public Mono<ProductDto> saveProduct(@RequestBody Mono<ProductDto> productDto) {
		return service.saveProduct(productDto);
	}

	@PutMapping("/update/{id}")
	public Mono<ProductDto> updateProduct(@RequestBody Mono<ProductDto> productDtoMono, @PathVariable String id) {
		return service.updateProduct(productDtoMono, id);
	}

	@DeleteMapping("/delete/{id}")
	public Mono<Void> deleteProduct(@PathVariable String id) {
		return service.deleteProduct(id);
	}

}
