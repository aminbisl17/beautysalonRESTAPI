package com.example.beautysalonRESTAPI.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.example.beautysalonRESTAPI.Configuration.TenantContext;

import java.net.URLEncoder;
@Service
public class BlobStorageService {

        private final BlobContainerClient containerClient;

            public BlobStorageService(
            @Value("${azure.storage.connection-string}") String connectionString) {

        BlobServiceClient blobServiceClient =
                new BlobServiceClientBuilder()
                        .connectionString(connectionString)
                        .buildClient();

        containerClient =
                blobServiceClient.getBlobContainerClient("beautysalon-images");
    }


public String uploadImage(MultipartFile image) throws IOException {

    String tenantKey = TenantContext.getTenantKey();

    if (tenantKey == null || tenantKey.isBlank()) {
        throw new IllegalStateException("Tenant key is missing");
    }

    String fileName = System.currentTimeMillis() + "_"
            + image.getOriginalFilename();

    String blobName = "SherbimetImgPath/"
            + tenantKey + "/"
            + fileName;

    BlobClient blobClient = containerClient.getBlobClient(blobName);

    blobClient.upload(
            new ByteArrayInputStream(image.getBytes()),
            image.getSize(),
            true
    );

    return fileName;
}


public String getImage(String imagePath) {

    if (imagePath == null || imagePath.isBlank()) {
        return null;
    }

    String tenantKey = TenantContext.getTenantKey();

    if (tenantKey == null || tenantKey.isBlank()) {
        throw new IllegalStateException("Tenant key is missing");
    }

    return "https://blobstorageamin.blob.core.windows.net/"
            + "beautysalon-images/SherbimetImgPath/"
            + tenantKey + "/"
            + URLEncoder.encode(imagePath, StandardCharsets.UTF_8)
                    .replace("+", "%20");
}

}