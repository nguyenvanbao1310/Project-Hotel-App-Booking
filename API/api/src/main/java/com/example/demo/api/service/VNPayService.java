package com.example.demo.api.service;

import com.example.demo.api.components.VNPayConfig;
import com.example.demo.api.components.VNPayUtils;
import com.example.demo.api.dto.PaymentDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class VNPayService {

    private final VNPayConfig vnPayConfig;
    private final VNPayUtils vnPayUtils;

    public VNPayService(VNPayConfig vnPayConfig, VNPayUtils vnPayUtils) {
        this.vnPayConfig = vnPayConfig;
        this.vnPayUtils = vnPayUtils;
    }

    public String createPaymentUrl(PaymentDTO paymentRequest, HttpServletRequest request) {
        // Tạo tham số cho URL thanh toán
        MultiValueMap<String, String> fields = new LinkedMultiValueMap<>();
        fields.add("vnp_Version", "2.1.0");
        fields.add("vnp_TmnCode", vnPayConfig.getVnpTmnCode());
        fields.add("vnp_Amount", String.valueOf(paymentRequest.getAmount()));
        fields.add("vnp_Currency", paymentRequest.getCurrency());
        fields.add("vnp_TxnRef", vnPayUtils.getRandomNumber(8));
        fields.add("vnp_OrderInfo", paymentRequest.getOrderInfo());
        fields.add("vnp_OrderType", "other"); // Loại đơn hàng, có thể thay đổi
        fields.add("vnp_Locale", "vn");
        fields.add("vnp_ReturnUrl", vnPayConfig.getVnpReturnUrl());
        fields.add("vnp_IpAddr", vnPayUtils.getIpAddress(request));
        fields.add("vnp_CreateDate", vnPayUtils.getCurrentDateTime());

        // Chuyển MultiValueMap thành Map<String, String>
        Map<String, String> singleValueMap = new HashMap<>();
        for (Map.Entry<String, List<String>> entry : fields.entrySet()) {
            if (entry.getValue() != null && !entry.getValue().isEmpty()) {
                singleValueMap.put(entry.getKey(), entry.getValue().get(0)); // Chọn phần tử đầu tiên
            }
        }

        // Tính toán mã bảo mật HMAC SHA512
        String secureHash = vnPayUtils.hashAllFields(singleValueMap);

        // Thêm mã bảo mật vào tham số
        fields.add("vnp_SecureHash", secureHash);

        // Tạo URL thanh toán
        StringBuilder paymentUrl = new StringBuilder(vnPayConfig.getVnpPayUrl());
        paymentUrl.append("?").append(fields.toString().replace(",", "&").replace("=", "="));

        return paymentUrl.toString();
    }

}
