package com.epam.decorator.decorate_service.service;

import com.epam.decorator.decorate_service.model.Photo;
import com.epam.decorator.decorate_service.repository.PhotoRepository;
import com.epam.decorator.decorate_service.service.PhotoServiceSendler;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
@Service
public class PhotoServiceSendlerImpl implements PhotoServiceSendler {
    @Autowired
    PhotoRepository photoRepo;


    @Override
    public String addPhoto(String title, MultipartFile file) {

        Photo photo = new Photo(title);
        try{
            photo.setImage(new Binary(BsonBinarySubType.BINARY, file.getBytes()));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        photo = photoRepo.insert(photo);
        return photo.getId();
    }
}
