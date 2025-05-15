package com.example.demo.api.controller;


import com.example.demo.api.Security.PasswordEncoder;
import com.example.demo.api.dto.AccountDTO;
import com.example.demo.api.dto.GuestDTO;
import com.example.demo.api.dto.LoginRequest;
import com.example.demo.api.dto.RegisterRequest;
import com.example.demo.api.entity.Account;
import com.example.demo.api.entity.Guest;
import com.example.demo.api.entity.Person;
import com.example.demo.api.enums.EnumRole;
import com.example.demo.api.repository.AccountRepository;
import com.example.demo.api.repository.PersonRepository;
import com.example.demo.api.service.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;

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
    @Autowired
    private PersonRepository personRepo;
    @Autowired
    private AccountRepository accountRepo;
    @Autowired
    private GuestServiceImpl guestServiceImpl;

    @PostMapping("add_account")
    public Account addAccount(@RequestBody Account account) {
        return accountService.createAccount(account);
    }

    @GetMapping
    public List<Account> getAllAccounts() {
        return accountService.getAllAccounts();
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody LoginRequest request) {
        String email = request.getEmail();
        String password = request.getPassword();

        if (email == null || email.isEmpty() || password == null || password.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("message", "Email và mật khẩu không được để trống"));
        }

        Optional<Account> accountOptional = accountServiceImpl.findByEmail(email);
        if (accountOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "Email không tồn tại"));
        }

        Account account = accountOptional.get();

        if (!PasswordEncoder.checkPassword(password, account.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Mật khẩu không chính xác"));
        }

        AccountDTO accountDTO = new AccountDTO(account);

        // ✅ Thêm đoạn này để lấy guestDTO
        Optional<Guest> guestOptional = guestServiceImpl.findByAccount(account);
        GuestDTO guestDTO = guestOptional.map(GuestDTO::new).orElse(null);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Đăng nhập thành công!");
        response.put("account", accountDTO);
        response.put("guest", guestDTO); // ✅ Gửi cả guest về client

        return ResponseEntity.ok(response);
    }



    @PostMapping("/register")
    public ResponseEntity<String> registerAccount( @Valid @RequestBody RegisterRequest request,
                                                   BindingResult result) {
        // Kiểm tra lỗi validation
        if (result.hasErrors()) {
            // Lấy thông báo lỗi từ BindingResult và trả về cho người dùng
            StringBuilder errorMessages = new StringBuilder();
            result.getAllErrors().forEach(error -> {
                errorMessages.append(error.getDefaultMessage()).append("\n");
            });
            return ResponseEntity.badRequest().body(errorMessages.toString());
        }

        if (accountRepo.existsByEmail(request.getEmail())) {
            return ResponseEntity.badRequest().body("Email đã tồn tại!");
        }


        String otp = otpService.generateOtp();
        String emailResponse = otpService.sendOtp(request.getEmail(), otp);

        if ("Success".equals(emailResponse)) {
            otpService.saveOtpForVerification(request.getEmail(), otp);
            otpService.cacheRegisterRequest(request.getEmail(), request); // Lưu tạm thông tin
            return ResponseEntity.ok("OTP đã được gửi qua email. Vui lòng nhập mã OTP để hoàn tất đăng ký.");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi khi gửi OTP");
        }
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<String> verifyOtp(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String otp = request.get("otp");

        String storedOtp = otpService.getOtpFromMemoryOrDb(email);

        if (!otp.equals(storedOtp)) {
            return ResponseEntity.badRequest().body("Mã OTP không chính xác.");
        }

        // Lấy lại thông tin đã đăng ký trước đó
        RegisterRequest cachedRequest = otpService.getCachedRegisterRequest(email);
        if (cachedRequest == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Không tìm thấy thông tin đăng ký. Vui lòng thử lại.");
        }

        // Tạo Person + Account như ban đầu
        Person person = new Guest(); // hoặc Admin
        person.setId(generatePersonId());
        person.setFullname(cachedRequest.getFullname());
        person.setCccd(cachedRequest.getCccd());
        person.setAddress(cachedRequest.getAddress());
        person.setGender(cachedRequest.getGender());

        Account account = new Account();
        account.setId(generateAccountId());
        account.setUsername(cachedRequest.getUsername());
        account.setEmail(cachedRequest.getEmail());
        account.setPhone(cachedRequest.getPhone());
        account.setPassword(PasswordEncoder.hashPassword(cachedRequest.getPassword()));
        account.setRole(EnumRole.GUEST); // hoặc USER/ADMIN
        account.setPerson(person);

        person.setAccount(account);
        personRepo.save(person); // cascade lưu cả account

        // Xóa khỏi cache nếu cần
        otpService.removeOtp(email);

        return ResponseEntity.ok("Đăng ký thành công!");
    }
    @PostMapping("/resend-otp")
    public ResponseEntity<Map<String, String>> resendOtp(@RequestBody Map<String, String> request) {
        String email = request.get("email");

        Map<String, String> response = new HashMap<>();

        if (email == null || email.isEmpty()) {
            response.put("message", "Email không được để trống");
            return ResponseEntity.badRequest().body(response);
        }

        String newOtp = otpService.generateOtp();
        String result = otpService.sendOtp(email, newOtp);

        if ("Success".equals(result)) {
            otpService.saveOtpForVerification(email, newOtp);
            response.put("message", "Mã OTP mới đã được gửi thành công");
            return ResponseEntity.ok(response);
        } else {
            response.put("message", "Gửi email thất bại");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }


    // Hàm sinh ID
    private String generateAccountId() {
        long count = accountRepo.count() + 1;
        return String.format("G%03d", count);
    }

    private String generatePersonId() {
        long count = personRepo.count() + 1;
        return String.format("P%03d", count);
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
        otpService.saveOtpForVerification(email, otp);

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
