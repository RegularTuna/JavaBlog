package com.legendaryblog.blog.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class FileUploadService {

    @Autowired
    private Cloudinary cloudinary;

    public String uploadImage(MultipartFile file) throws IOException {
        Map params = ObjectUtils.asMap(
                "use_filename", true,
                "unique_filename", true, // Best to keep true for blogs to avoid overwriting
                "folder", "blog_covers"  // Organizes your Cloudinary dashboard
        );

        // Uploading the bytes from the multipart file
        Map uploadResult = cloudinary.uploader().upload(file.getBytes(), params);

        // Return the secure URL to save in your Database
        return uploadResult.get("secure_url").toString();
    }
}
