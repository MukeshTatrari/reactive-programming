package com.reactive.api.router;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.reactive.api.handler.CustomerHandler;
import com.reactive.api.handler.CustomerStreamHandler;

@Configuration
public class RouterConfig {

	@Autowired
	private CustomerHandler customerHandler;
	
	@Autowired
	private CustomerStreamHandler handler;

	@Bean
	public RouterFunction<ServerResponse> routerFunction() {
		return RouterFunctions.route()
				.GET("/router/customers", customerHandler::loadCustomers)
				.GET("/router/customers/streams", handler::getCustomers)
				.GET("/router/customers/{input}", customerHandler::findCustomerById)
				.POST("/router/customers/save", customerHandler::saveCustomer)
				.build();
	}

}
