package com.example.demo.api.controller;

import com.example.demo.api.components.VNPayUtils;
import com.example.demo.api.dto.PaymentDTO;
import com.example.demo.api.dto.ResponseObject;
import com.example.demo.api.service.BookingOrderService;
import com.example.demo.api.service.VNPayService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/payments")
public class PaymentController {

    private final VNPayService vnPayService;

    @Autowired
    private VNPayUtils vnPayUtils;

    @Autowired
    private BookingOrderService bookingOrderService;

    @PostMapping("/create_payment_url")
    public ResponseEntity<ResponseObject> createPayment(@RequestBody PaymentDTO paymentRequest, HttpServletRequest request) {
        try {
            // Tạo URL thanh toán với các thông tin từ paymentRequest
            String paymentUrl = vnPayService.createPaymentUrl(paymentRequest, request);

            return ResponseEntity.ok(ResponseObject.builder()
                    .status(HttpStatus.OK)
                    .message("Payment URL generated successfully.")
                    .data(paymentUrl)
                    .build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseObject.builder()
                            .status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .message("Error generating payment URL: " + e.getMessage())
                            .build());
        }
    }


    @GetMapping("/vnpay-return")
    public void handleVnPayReturn(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String vnp_ResponseCode = request.getParameter("vnp_ResponseCode");
        String vnp_TxnRef = request.getParameter("vnp_TxnRef"); // idOrder
        String vnp_SecureHash = request.getParameter("vnp_SecureHash");

        boolean isValidChecksum = verifyChecksum(request); // kiểm tra checksum với VNPayUtils

        if (isValidChecksum) {
            if ("00".equals(vnp_ResponseCode)) {
                // ✅ Thanh toán thành công
                bookingOrderService.updatePaymentStatus(vnp_TxnRef, true);
            } else {
                // ❌ Thanh toán thất bại
                bookingOrderService.updatePaymentStatus(vnp_TxnRef, false);
            }

            // Chuyển hướng về app (Android deep link)
            String redirectUrl = "yourapp://payment?txnRef=" + vnp_TxnRef +
                    "&responseCode=" + vnp_ResponseCode;
            response.sendRedirect(redirectUrl);
        } else {
            response.sendRedirect("yourapp://payment?error=invalid_checksum");
        }
    }



    // Phương thức kiểm tra checksum từ phản hồi của VNPay
    private boolean verifyChecksum(HttpServletRequest request) {
        Map<String, String> fields = new HashMap<>();

        // Lấy tất cả tham số từ request
        Enumeration<String> paramNames = request.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String paramName = paramNames.nextElement();
            String paramValue = request.getParameter(paramName);

            if (!paramName.equals("vnp_SecureHash") && !paramName.equals("vnp_SecureHashType")) {
                if (paramValue != null && !paramValue.isEmpty()) {
                    fields.put(paramName, paramValue);
                }
            }
        }

        // Lấy mã bảo mật từ response
        String vnp_SecureHash = request.getParameter("vnp_SecureHash");

        // Tính toán mã bảo mật từ các tham số
        String calculatedHash = vnPayUtils.hashAllFields(fields);

        // So sánh mã bảo mật tính toán với mã bảo mật nhận được
        return calculatedHash.equals(vnp_SecureHash);
    }



}

