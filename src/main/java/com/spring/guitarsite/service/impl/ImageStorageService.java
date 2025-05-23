package com.spring.guitarsite.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class ImageStorageService {

    private final String uploadDir = "src/main/resources/static/img/";

    public String saveImage(MultipartFile imageFile, String currentImagePath) throws IOException {

        if (imageFile == null || imageFile.isEmpty()) {
            return currentImagePath;
        }

        if (currentImagePath != null && !currentImagePath.isEmpty()) {
            deleteImage(currentImagePath);
        }

        Files.createDirectories(Paths.get(uploadDir));

        String originalFilename = imageFile.getOriginalFilename();
        String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String newFilename = "guitar_" + UUID.randomUUID() + fileExtension;

        Path filePath = Paths.get(uploadDir + newFilename);
        Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        return "img/" + newFilename;
    }

    public void deleteImage(String imagePath) throws IOException {
        if (imagePath != null && !imagePath.isEmpty()) {
            Path path = Paths.get("src/main/resources/static/" + imagePath);
            if (Files.exists(path)) {
                Files.delete(path);
            }
        }
    }
}