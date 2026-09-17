package com.example.beautysalonRESTAPI.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;

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

        String fileName =
                System.currentTimeMillis() + "_" + image.getOriginalFilename();

        String blobName = "SherbimetImgPath/" + fileName;

        BlobClient blobClient =
                containerClient.getBlobClient(blobName);

        blobClient.upload(
                new ByteArrayInputStream(image.getBytes()),
                image.getSize(),
                true
        );

        return fileName;
    }
}