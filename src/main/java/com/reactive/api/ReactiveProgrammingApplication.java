package com.reactive.api;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.reactive.api.config.TwilioConfig;
import com.twilio.Twilio;

@SpringBootApplication
public class ReactiveProgrammingApplication {

	@Autowired
	private TwilioConfig twilioConfig;
	
	public static void main(String[] args) {
		SpringApplication.run(ReactiveProgrammingApplication.class, args);
	}

	@PostConstruct
	public void initTwilio() {
		Twilio.init(twilioConfig.getAccountSid(), twilioConfig.getAuthToken());
	}
}
