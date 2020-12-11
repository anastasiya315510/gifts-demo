package com.epam.decorator.decorate_service.service;


import com.epam.decorator.decorate_service.model.*;
import org.springframework.web.multipart.MultipartFile;


public interface PhotoServiceHandler {
        ImageResponceDTO getPhotoUser(EventDTO eventDTO, String template);


}
