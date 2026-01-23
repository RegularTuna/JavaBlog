package com.legendaryblog.blog.config;

import com.cloudinary.Cloudinary;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryConfig {

    // This pulls the value from your application.properties
    @Value("${cloudinary.url}")
    private String cloudinaryUrl;

    @Bean
    public Cloudinary cloudinary() {
        // This creates a single instance (Bean) that you can @Autowired later
        return new Cloudinary(cloudinaryUrl);
    }
}
