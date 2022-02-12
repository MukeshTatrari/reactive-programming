package com.reactive.api.service;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reactive.api.config.TwilioConfig;
import com.reactive.api.dto.OtpStatus;
import com.reactive.api.dto.PasswordResetRequestDto;
import com.reactive.api.dto.PasswordResetResponseDto;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import reactor.core.publisher.Mono;

@Service
public class TwilioService {

	@Autowired
	private TwilioConfig twilioConfig;

	Map<String, String> otpMap = new HashMap<>();

	public Mono<PasswordResetResponseDto> sentOTPForPasswordReset(PasswordResetRequestDto passwordResetRequestDto) {

		PasswordResetResponseDto passwordResetResponseDto = null;
		try {
			PhoneNumber to = new PhoneNumber(passwordResetRequestDto.getPhoneNumber());
			PhoneNumber from = new PhoneNumber(twilioConfig.getTrialNumber());
			String otp = generateOTP();
			String otpMessage = "Dear Customer , Your OTP is ##" + otp
					+ "##. Use this Passcode to complete your transaction. Thank You.";
			Message message = Message.creator(to, from, otpMessage).create();
			otpMap.put(passwordResetRequestDto.getUserName(), otp);
			passwordResetResponseDto = new PasswordResetResponseDto(otpMessage, OtpStatus.DELIVERED);
		} catch (Exception ex) {
			passwordResetResponseDto = new PasswordResetResponseDto(ex.getMessage(), OtpStatus.FAILED);
		}
		return Mono.just(passwordResetResponseDto);
	}

	public Mono<String> validateOTP(String userInputOtp, String userName) {
		if (userInputOtp.equals(otpMap.get(userName))) {
			otpMap.remove(userName, userInputOtp);
			return Mono.just("Valid OTP please proceed with your transaction !");
		} else {
			return Mono.error(new IllegalArgumentException("Invalid otp please retry !"));
		}
	}

	private String generateOTP() {
		return new DecimalFormat("000000").format(new Random().nextInt(999999));
	}
}
