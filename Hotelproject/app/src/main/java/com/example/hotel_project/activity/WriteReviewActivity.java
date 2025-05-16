package com.example.hotel_project.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.hotel_project.R;
import com.example.hotel_project.api.ReviewApiService;
import com.example.hotel_project.model.AccountDTO;
import com.example.hotel_project.model.BookingOrderDTO;
import com.example.hotel_project.model.GuestDTO;
import com.example.hotel_project.model.ReviewDTO;
import com.example.hotel_project.retrofit.RetrofitClient;
import com.example.hotel_project.sharedprefs.SharedPreferencesManager;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WriteReviewActivity extends AppCompatActivity {

    private TextView txtHotelName, txtHotelAddress, txtHotelCheckin;

    private ImageView imgHotel;
    private RatingBar ratingLocation, ratingService;
    private EditText reviewText;
    private Button selectPicturesBtn, submitBtn;

    private ImageView imgPreview1, imgPreview2, imgPreview3;

    private static final int PICK_IMAGES_REQUEST = 1;

    private ArrayList<Uri> selectedImages = new ArrayList<>();

    private ImageView selectedImageView;
    private ActivityResultLauncher<Intent> pickImageLauncher;

    private GuestDTO guestDTO;

    private AccountDTO accountDTO;

    private BookingOrderDTO bookingOrder;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_review);

        bookingOrder = (BookingOrderDTO) getIntent().getSerializableExtra("bookingOrder");
        guestDTO = SharedPreferencesManager.getGuestDTO(this);
        accountDTO = SharedPreferencesManager.getAccountDTO(this);


        txtHotelName = findViewById(R.id.text_hotel_name);
        txtHotelAddress = findViewById(R.id.text_hotel_address);
        txtHotelCheckin = findViewById(R.id.text_checkin);
        imgHotel = findViewById(R.id.img_pic_hotel);


        ratingLocation = findViewById(R.id.rating_location);
        LayerDrawable stars = (LayerDrawable) ratingLocation.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(android.graphics.Color.parseColor("#FFEB3B"), android.graphics.PorterDuff.Mode.SRC_ATOP); // đã chọn
        stars.getDrawable(1).setColorFilter(android.graphics.Color.parseColor("#FFECB3"), android.graphics.PorterDuff.Mode.SRC_ATOP); // 1 phần
        stars.getDrawable(0).setColorFilter(android.graphics.Color.LTGRAY, android.graphics.PorterDuff.Mode.SRC_ATOP); // chưa chọn

        ratingService = findViewById(R.id.rating_service);
        reviewText = findViewById(R.id.review_text);
        selectPicturesBtn = findViewById(R.id.select_pictures);
        submitBtn = findViewById(R.id.btn_submit);

        txtHotelName.setText(bookingOrder.getHotelOrder().getName());
        txtHotelAddress.setText(bookingOrder.getHotelOrder().getCity());


        String dateStartStr = bookingOrder.getDateStart();  // Giả sử đây là chuỗi ngày như "2025-04-01T14:00:00"
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        LocalDateTime dateStart = LocalDateTime.parse(dateStartStr, inputFormatter);

        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formattedDate = dateStart.format(outputFormatter);

        txtHotelCheckin.setText("Checkin: " + formattedDate);

        String fullUrl = RetrofitClient.IMG_URL
                + bookingOrder.getHotelOrder().getHotel_image_url()
                + "?v=" + System.currentTimeMillis();

        Glide.with(this)
                .load(fullUrl)
                .into(imgHotel);

        selectPicturesBtn = findViewById(R.id.select_pictures);
        imgPreview1 = findViewById(R.id.img_preview_1);
        imgPreview2 = findViewById(R.id.img_preview_2);
        imgPreview3 = findViewById(R.id.img_preview_3);

        imgPreview1.setOnClickListener(v -> {
            selectedImageView = imgPreview1;
            openImagePicker();
        });

        imgPreview2.setOnClickListener(v -> {
            selectedImageView = imgPreview2;
            openImagePicker();
        });

        imgPreview3.setOnClickListener(v -> {
            selectedImageView = imgPreview3;
            openImagePicker();
        });

        pickImageLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Uri imageUri = result.getData().getData();
                        if (imageUri != null && selectedImageView != null) {
                            selectedImageView.setImageURI(imageUri);

                            // Lưu URI tương ứng với imgPreview
                            if (selectedImageView == imgPreview1) {
                                if (selectedImages.size() > 0) selectedImages.set(0, imageUri);
                                else selectedImages.add(0, imageUri);
                            } else if (selectedImageView == imgPreview2) {
                                if (selectedImages.size() > 1) selectedImages.set(1, imageUri);
                                else {
                                    while (selectedImages.size() < 1) selectedImages.add(null);
                                    selectedImages.add(1, imageUri);
                                }
                            } else if (selectedImageView == imgPreview3) {
                                if (selectedImages.size() > 2) selectedImages.set(2, imageUri);
                                else {
                                    while (selectedImages.size() < 2) selectedImages.add(null);
                                    selectedImages.add(2, imageUri);
                                }
                            }
                        }
                    }
                });
        submitBtn.setOnClickListener(v -> {
            submitReview();
        });
    }
    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickImageLauncher.launch(intent);
    }

    private void submitReview() {

        float locationRating = ratingLocation.getRating();
        float serviceRating = ratingService.getRating();
        String content = reviewText.getText().toString();
        if (content.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập nội dung đánh giá", Toast.LENGTH_SHORT).show();
            return;
        }

        if (locationRating == 0 && serviceRating == 0) {
            Toast.makeText(this, "Vui lòng đánh giá ít nhất một mục", Toast.LENGTH_SHORT).show();
            return;
        }

        double rating = (ratingLocation.getRating() + ratingService.getRating()) / 2;

        ReviewDTO reviewDTO = new ReviewDTO();
        reviewDTO.setIdReview(null);
        reviewDTO.setAccountId(accountDTO.getId());
        reviewDTO.setUserName(accountDTO.getUsername());
        reviewDTO.setBookingOrderId(bookingOrder.getIdOrder());
        reviewDTO.setHotelId(bookingOrder.getHotelId());
        reviewDTO.setRating(rating);
        reviewDTO.setRateLocation((int) ratingLocation.getRating());
        reviewDTO.setRateService((int) ratingService.getRating());
        reviewDTO.setContent(content);
        reviewDTO.setReviewDate(LocalDate.now());

        reviewDTO.setImageReviews(new ArrayList<>());
        Gson gson = new Gson();
        Log.d("ReviewSubmit", "JSON gửi: " + gson.toJson(reviewDTO));

        ArrayList<File> imageFiles = new ArrayList<>();
        imageFiles.add(getImageFileFromImageView(imgPreview1));
        imageFiles.add(getImageFileFromImageView(imgPreview2));
        imageFiles.add(getImageFileFromImageView(imgPreview3));
        imageFiles.removeIf(file -> file == null);

        if (!imageFiles.isEmpty()) {
            uploadReviewImages(reviewDTO, imageFiles);
        } else {
            uploadReviewWithoutImages(reviewDTO);
        }
    }

    private void uploadReviewWithoutImages(ReviewDTO reviewDTO) {
        ReviewApiService reviewApi = RetrofitClient.getRetrofit().create(ReviewApiService.class);
        Call<ReviewDTO> call = reviewApi.addReview(reviewDTO);
        call.enqueue(new Callback<ReviewDTO>() {
            @Override
            public void onResponse(Call<ReviewDTO> call, Response<ReviewDTO> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(WriteReviewActivity.this, "Đánh giá thành công!", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    try {
                        String errorBody = response.errorBody().string();
                        Log.e("ReviewError", errorBody);
                        Toast.makeText(WriteReviewActivity.this, "Lỗi: " + errorBody, Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ReviewDTO> call, Throwable t) {
                Toast.makeText(WriteReviewActivity.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void uploadReviewImages(ReviewDTO reviewDTO, List<File> imageFiles) {
        ReviewApiService apiService = RetrofitClient.getRetrofit().create(ReviewApiService.class);

        List<MultipartBody.Part> imageParts = new ArrayList<>();
        for (File file : imageFiles) {
            RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);
            MultipartBody.Part body = MultipartBody.Part.createFormData("files", file.getName(), requestFile);
            imageParts.add(body);
        }

        Call<List<String>> call = apiService.uploadReviewWithImages(imageParts);
        call.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                if (response.isSuccessful()) {
                    List<String> imageUrls = response.body();
                    reviewDTO.setImageReviews(imageUrls);  // Gắn URL ảnh vào DTO

                    // Gọi lại API addReview sau khi có URL ảnh
                    uploadReviewWithoutImages(reviewDTO);
                } else {
                    Toast.makeText(WriteReviewActivity.this, "Tải ảnh thất bại", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                Toast.makeText(WriteReviewActivity.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }




    private File getImageFileFromImageView(ImageView imageView) {
        if (imageView.getDrawable() == null) return null;

        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        File file = new File(getCacheDir(), "review_image_" + System.currentTimeMillis() + ".jpg");

        try (FileOutputStream outStream = new FileOutputStream(file)) {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, outStream);
            return file;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }









}
