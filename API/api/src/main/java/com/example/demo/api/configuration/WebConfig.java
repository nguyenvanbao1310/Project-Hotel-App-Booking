package com.example.demo.api.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/imgReview/**")
                .addResourceLocations("file:D:/Nam3/DACKLTDD/Project-Hotel-App-Booking/API/api/uploads/images/imgReview/");
    }
}