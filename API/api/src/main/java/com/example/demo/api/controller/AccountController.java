package com.example.demo.api.controller;


import com.example.demo.api.Security.PasswordEncoder;
import com.example.demo.api.entity.Account;
import com.example.demo.api.enums.EnumRole;
import com.example.demo.api.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

@RestController
@RequestMapping("/api/account")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountServiceImpl accountServiceImpl;
    @Autowired
    private OtpService otpService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private IAccountService accountServiceI;

    @PostMapping("add_account")
    public Account addAccount(@RequestBody Account account) {
        return accountService.createAccount(account);
    }

    @GetMapping
    public List<Account> getAllAccounts() {
        return accountService.getAllAccounts();
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String password = request.get("password");

        if (email == null || email.isEmpty() || password == null || password.isEmpty()) {
            return ResponseEntity.badRequest().body("Email và mật khẩu không được để trống");
        }

        Optional<Account> accountOptional = accountServiceImpl.findByEmail(email);  // thay vì iUserSv
        if (accountOptional.isPresent()) {
            Account account = accountOptional.get();

            // Kiểm tra mật khẩu bằng PasswordEncoder
            if (PasswordEncoder.checkPassword(password, account.getPassword())) {
                return ResponseEntity.ok("Đăng nhập thành công!");
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Mật khẩu không chính xác");
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Email không tồn tại");
        }
    }
    @PostMapping("/register")
    public ResponseEntity<String> registerAccount(@RequestBody Account account) {
        if (accountServiceI.existsByEmail(account.getEmail())) {
            return new ResponseEntity<>("Email đã tồn tại!", HttpStatus.BAD_REQUEST);
        }

        String otp = otpService.generateOtp();
        String emailResponse = otpService.sendOtp(account.getEmail(), otp);

        if (emailResponse.equals("Success")) {
            otpService.saveOtpForVerification(account.getEmail(), otp);
            // Tạm thời lưu thông tin vào memory hoặc cache nếu cần
            return new ResponseEntity<>("OTP đã được gửi qua email. Vui lòng nhập mã OTP để hoàn tất đăng ký.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Lỗi khi gửi OTP", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/verify-otp")
    public ResponseEntity<String> verifyOtp(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String otp = request.get("otp");

        String storedOtp = otpService.getOtpFromMemoryOrDb(email);

        if (storedOtp != null && storedOtp.equals(otp)) {
            Account account = new Account();
            account.setEmail(email);
            account.setUsername(request.get("username"));
            account.setPhone(request.get("phone"));
            account.setPassword(request.get("password"));
            account.setRole(EnumRole.GUEST); // hoặc ADMIN nếu cần

            accountServiceI.addAccount(account);

            return new ResponseEntity<>("Đăng ký thành công!", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Mã OTP không chính xác.", HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/forgot-password")
    public ResponseEntity<?> sendOtpForPasswordReset(@RequestBody Map<String, String> request) {
        String email = request.get("email");

        if (email == null || email.isEmpty()) {
            return ResponseEntity.badRequest().body("Email không được để trống");
        }

        Optional<Account> accountOptional = accountServiceI.findByEmail(email);
        if (accountOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Email không tồn tại");
        }

        String otp = String.format("%04d", new Random().nextInt(9999));
        otpService.saveOtp(email, otp);

        boolean isEmailSent = emailService.sendEmail(email, "Mã OTP khôi phục mật khẩu", "Mã OTP của bạn là: " + otp);
        if (!isEmailSent) {
            return new ResponseEntity<>("Không thể gửi email. Vui lòng thử lại sau.", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return ResponseEntity.ok("Mã OTP đã được gửi tới email của bạn");
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String otp = request.get("otp");
        String newPassword = request.get("newPassword");

        if (email == null || otp == null || newPassword == null) {
            return ResponseEntity.badRequest().body("Email, OTP và mật khẩu mới không được để trống");
        }

        String storedOtp = otpService.getOtpFromMemoryOrDb(email);
        if (storedOtp != null && storedOtp.equals(otp)) {
            Optional<Account> accountOptional = accountServiceI.findByEmail(email);
            if (accountOptional.isPresent()) {
                Account account = accountOptional.get();
                String hashedPassword = PasswordEncoder.hashPassword(newPassword);
                account.setPassword(hashedPassword);
                accountServiceI.updateAccount(account);
                return ResponseEntity.ok("Mật khẩu đã được đặt lại thành công");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Người dùng không tồn tại");
            }
        } else {
            return new ResponseEntity<>("Mã OTP không chính xác hoặc đã hết hạn.", HttpStatus.BAD_REQUEST);
        }
    }


}
