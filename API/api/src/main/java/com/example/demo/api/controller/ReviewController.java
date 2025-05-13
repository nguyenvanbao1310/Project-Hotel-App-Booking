package com.example.demo.api.controller;


import com.example.demo.api.dto.ReviewDTO;
import com.example.demo.api.entity.Review;
import com.example.demo.api.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


@RestController
@RequestMapping("/api/reviews")
public class ReviewController {
    @Autowired
    private ReviewService reviewService;


    @GetMapping()
    public List<Review> getReviews() {return reviewService.getAllReviews();}

    @GetMapping("/hotel/{hotelId}")
    public ResponseEntity<List<ReviewDTO>> getReviewsByHotel(@PathVariable String hotelId) {
        return ResponseEntity.ok(reviewService.getReviewsByHotelId(hotelId));
    }

    @GetMapping("/{account_id}")
    public ResponseEntity<List<ReviewDTO>> getReviewsByAccount(@PathVariable String account_id) {
        return ResponseEntity.ok(reviewService.getReviewsByAccountId(account_id));
    }

    @PostMapping("/add_review")
    public ResponseEntity<ReviewDTO> createReview(@RequestBody ReviewDTO dto) {
        ReviewDTO createdReviewDTO = reviewService.createReview(dto);
        return ResponseEntity.ok(createdReviewDTO);
    }

    @PostMapping("/uploadImages")
    public ResponseEntity<List<String>> uploadImages(@RequestParam("files") List<MultipartFile> files) {
        List<String> fileUrls = new ArrayList<>();

        // Kiểm tra xem danh sách file có rỗng không
        if (files.isEmpty()) {
            return ResponseEntity.badRequest().body(Collections.singletonList("No files to upload"));
        }

        try {
            // Thư mục lưu trữ ảnh
            String directoryPath = "uploads/images/imgReview/";
            File directory = new File(directoryPath);
            if (!directory.exists()) {
                directory.mkdirs(); // Tạo thư mục nếu chưa có
            }

            for (MultipartFile file : files) {
                String fileName = file.getOriginalFilename();

                // Kiểm tra tên file
                if (fileName == null || fileName.isEmpty()) {
                    return ResponseEntity.badRequest().body(Collections.singletonList("File name is empty"));
                }

                // Kiểm tra file có dữ liệu hay không
                if (file.isEmpty()) {
                    return ResponseEntity.badRequest().body(Collections.singletonList("File is empty"));
                }

                // Tạo tên file duy nhất (ví dụ bằng UUID hoặc thêm timestamp)
                String uniqueFileName = System.currentTimeMillis() + "-" + fileName;
                Path filePath = Paths.get(directoryPath + uniqueFileName);

                System.out.println("Saved file at: " + filePath.toAbsolutePath());


                // Lưu ảnh vào thư mục
                Files.write(filePath, file.getBytes());

                // URL của ảnh (URL này sẽ phụ thuộc vào cách bạn cấu hình Static Resource Mapping trong Spring Boot)
                String fileUrl = "/images/imgReview/" + uniqueFileName;
                fileUrls.add(fileUrl);
            }

            return ResponseEntity.status(HttpStatus.OK).body(fileUrls);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonList("Error uploading files: " + e.getMessage()));
        }
    }


}
