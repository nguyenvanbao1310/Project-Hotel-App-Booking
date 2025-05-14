package com.example.demo.api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentDTO {

    // ID của đơn hàng
    private Long orderId;

    // Số tiền thanh toán (vnp_Amount)
    private Long amount;

    // Mô tả sản phẩm hoặc dịch vụ
    private String orderInfo;

    // Mã tiền tệ, ví dụ: VND
    private String currency;

    // Thông tin khách hàng (tên, email, số điện thoại)
    private String customerName;
    private String customerEmail;
    private String customerPhone;

    // Địa chỉ khách hàng (nếu có)
    private String customerAddress;

    // Thời gian yêu cầu thanh toán
    private String createDate;
}