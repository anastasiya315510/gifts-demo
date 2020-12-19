package com.epam.decorator.decorate_service.serv;

import com.epam.decorator.decorate_service.model.Photo;
import com.epam.decorator.decorate_service.repository.PhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

public interface PhotoServiceCreator {
    String addPhoto(String title, MultipartFile file);


}
