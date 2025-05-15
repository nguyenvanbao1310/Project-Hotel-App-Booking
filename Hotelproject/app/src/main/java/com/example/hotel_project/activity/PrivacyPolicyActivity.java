package com.example.hotel_project.activity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.hotel_project.R;

public class PrivacyPolicyActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_policy);

        TextView textView = findViewById(R.id.privacyPolicyText);

        // Tạo nội dung với định dạng phong phú hơn
        String[] policyPoints = {
                "1. Thu thập thông tin",
                "Chúng tôi chỉ thu thập những thông tin cá nhân cần thiết để phục vụ cho việc đặt phòng và trải nghiệm của quý khách tại khách sạn.",

                "2. Bảo mật thông tin",
                "Mọi thông tin cá nhân đều được lưu trữ an toàn và cam kết không chia sẻ cho bên thứ ba nếu không có sự đồng ý của quý khách.",

                "3. Mục đích sử dụng",
                "Thông tin được sử dụng nhằm mục đích quản lý đặt phòng, cá nhân hóa dịch vụ và hỗ trợ chăm sóc khách hàng.",

                "4. Sử dụng Cookies",
                "Hệ thống có thể sử dụng cookies để cải thiện hiệu suất và trải nghiệm người dùng khi sử dụng ứng dụng hoặc website.",

                "5. Quyền của khách hàng",
                "Quý khách có quyền yêu cầu xem, chỉnh sửa hoặc xóa thông tin cá nhân của mình bất cứ lúc nào.",

                "6. Liên hệ",
                "Mọi thắc mắc liên quan đến chính sách bảo mật vui lòng liên hệ quầy lễ tân hoặc gửi email đến: hotro@khachsan.com"
        };

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < policyPoints.length; i++) {
            if (i % 2 == 0) { // Tiêu đề
                builder.append(policyPoints[i]).append("\n");
            } else { // Nội dung
                builder.append(policyPoints[i]).append("\n\n");
            }
        }

        builder.append("\nXin chân thành cảm ơn quý khách đã tin tưởng và sử dụng dịch vụ của chúng tôi!");

        SpannableString spannableString = new SpannableString(builder.toString());

        // Định dạng tiêu đề các mục
        int start = 0;
        for (String point : policyPoints) {
            if (point.startsWith("1.") || point.startsWith("2.") || point.startsWith("3.") ||
                    point.startsWith("4.") || point.startsWith("5.") || point.startsWith("6.")) {
                int end = start + point.length();
                spannableString.setSpan(new StyleSpan(Typeface.BOLD), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            start += point.length() + 1; // +1 cho ký tự xuống dòng
        }

        textView.setText(spannableString);
        textView.setTextColor(ContextCompat.getColor(this, R.color.text_dark_gray));
    }
}