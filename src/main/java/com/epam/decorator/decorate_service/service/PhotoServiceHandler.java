package com.epam.decorator.decorate_service.service;


import com.epam.decorator.decorate_service.model.Photo;
import com.epam.decorator.decorate_service.model.Response;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public interface PhotoServiceHandler {
        Response getPhoto(String title, Model model);
        Response createCard( String text);
        String addPhoto(String title, MultipartFile file, Model model);
        public List<Photo> getAll();
}
