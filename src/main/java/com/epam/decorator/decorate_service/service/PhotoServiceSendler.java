package com.epam.decorator.decorate_service.service;

import org.springframework.web.multipart.MultipartFile;

public interface PhotoServiceSendler {
    String addPhoto(String title, MultipartFile file);
}
