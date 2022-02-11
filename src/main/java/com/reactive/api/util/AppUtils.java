package com.reactive.api.util;

import org.springframework.beans.BeanUtils;

import com.reactive.api.dto.ProductDto;
import com.reactive.api.entity.Product;

public class AppUtils {

	public static ProductDto entityToDto(Product product) {

		ProductDto productDto = new ProductDto();
		BeanUtils.copyProperties(product, productDto);
		return productDto;
	}

	public static Product dtoToEntity(ProductDto productDto) {
		Product product = new Product();
		BeanUtils.copyProperties(productDto, product);
		return product;
	}
}
