package com.epam.decorator.decorate_service.serv;

import com.epam.decorator.decorate_service.controller.PhotoController;
import com.epam.decorator.decorate_service.model.Photo;
import com.epam.decorator.decorate_service.repository.PhotoRepository;
import com.epam.decorator.decorate_service.serv.PhotoServiceCreator;
import lombok.extern.slf4j.Slf4j;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
@Slf4j
@Service
public class PhotoServiceCreatorImpl implements PhotoServiceCreator {

    private static final Logger logger = LoggerFactory.getLogger(PhotoController.class);

    @Autowired
    PhotoRepository photoRepo;
    @Override
    public String addPhoto(String title, MultipartFile file) {

            Photo photo = new Photo(title);

            try {
                log.info("enter to addPhoto photo to database");
                photo.setImage(
                        new Binary(BsonBinarySubType.BINARY, file.getBytes()));
                photo = photoRepo.insert(photo);
                log.info("enter to  photo was send to database");
            } catch (IOException e) {
                log.info("error occurred send to database "+e.getMessage());

            }

            return photo.getId();
        }
    }

