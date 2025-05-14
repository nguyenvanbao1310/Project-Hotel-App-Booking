package com.example.demo.api.service;

import com.example.demo.api.dto.RegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class OtpService {

	private final Map<String, String> otpStorage = new HashMap<>();
	private final Map<String, RegisterRequest> registerRequestCache = new HashMap<>();

	@Autowired
	private JavaMailSender javaMailSender;

	// Sinh mã OTP 4 chữ số
	public String generateOtp() {
		Random rand = new Random();
		int otp = rand.nextInt(10000); // 0000 - 9999
		return String.format("%04d", otp);
	}

	// Gửi OTP qua email
	public String sendOtp(String email, String otp) {
		try {
			SimpleMailMessage message = new SimpleMailMessage();
			message.setFrom("your-email@example.com"); // <-- nhớ sửa
			message.setTo(email);
			message.setSubject("Mã OTP xác thực");
			message.setText("Mã OTP của bạn là: " + otp);

			javaMailSender.send(message);
			return "Success";
		} catch (Exception e) {
			e.printStackTrace();
			return "Error";
		}
	}

	// Lưu OTP vào bộ nhớ
	public void saveOtpForVerification(String email, String otp) {
		otpStorage.put(email, otp);
	}

	public String getOtpFromMemoryOrDb(String email) {
		return otpStorage.get(email);
	}

	public boolean verifyOtp(String email, String otp) {
		String storedOtp = otpStorage.get(email);
		return storedOtp != null && storedOtp.equals(otp);
	}

	// Lưu thông tin đăng ký tạm thời để xác thực OTP xong mới ghi DB
	public void cacheRegisterRequest(String email, RegisterRequest request) {
		registerRequestCache.put(email, request);
	}

	public RegisterRequest getCachedRegisterRequest(String email) {
		return registerRequestCache.get(email);
	}

	public void removeOtp(String email) {
		otpStorage.remove(email);
		registerRequestCache.remove(email);
	}
}
