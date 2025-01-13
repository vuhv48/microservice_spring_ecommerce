package com.e_commerce.product_service.service;

import com.e_commerce.product_service.model.Media;
import com.e_commerce.product_service.repository.MediaRepsitory;
import com.e_commerce.product_service.viewmodel.MediaVm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
@Transactional
@RequiredArgsConstructor
public class MediaService {
    public final MediaRepsitory mediaRepsitory;

    public MediaVm create(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        String filePath = "/Users/hoangvanvu/Downloads/" + fileName;
        File destFile = new File(filePath);
        file.transferTo(destFile);
        Media media = Media.builder()
                .fileName(fileName)
                .filePath(filePath)
                .fileType(file.getOriginalFilename())
                .build();
        Media savedMedia = mediaRepsitory.save(media);
        return MediaVm.fromModel(savedMedia);
    }
}
