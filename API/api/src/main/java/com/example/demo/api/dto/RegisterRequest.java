package com.example.demo.api.dto;

import lombok.Data;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Data
public class RegisterRequest {
    @NotBlank(message = "Họ tên không được để trống")
    private String fullname;

    @Pattern(regexp = "\\d{12}", message = "CCCD phải gồm đúng 12 chữ số")
    private String cccd;

    private String address;

    private Boolean gender;

    @Email(message = "Email không hợp lệ")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@gmail\\.com$", message = "Email phải là địa chỉ @gmail.com")
    private String email;

    @Pattern(regexp = "\\d{10}", message = "Số điện thoại phải gồm đúng 10 chữ số")
    private String phone;

    @NotBlank(message = "Username không được để trống")
    private String username;
    @Size(min = 6, message = "Mật khẩu phải có ít nhất 6 ký tự")
    private String password;

}
