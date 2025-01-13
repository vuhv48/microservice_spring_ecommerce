package com.e_commerce.product_service.controller;

import com.e_commerce.product_service.service.MediaService;
import com.e_commerce.product_service.viewmodel.MediaVm;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class MediaController {
    private final MediaService mediaService;

    @PostMapping(value ="/backoffice/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<MediaVm> uploadMedia(@RequestPart(value = "file") MultipartFile file) {
        try {
            MediaVm mediaVm = mediaService.create(file);
            return new ResponseEntity<>(mediaVm, HttpStatus.CREATED);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
