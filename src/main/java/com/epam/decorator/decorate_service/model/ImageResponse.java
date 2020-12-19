package com.epam.decorator.decorate_service.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

@Data
@Builder
public class ImageResponse {
    private byte[] image;
    private String self;
}