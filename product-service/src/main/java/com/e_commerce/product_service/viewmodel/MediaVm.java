package com.e_commerce.product_service.viewmodel;

import com.e_commerce.product_service.model.Media;

public record MediaVm(
        String fileName,
        String fileType,
        String filePath
) {
    public static MediaVm fromModel(Media media) {
        return new MediaVm(media.getFileName(), media.getFileType(), media.getFilePath());
    }
}
