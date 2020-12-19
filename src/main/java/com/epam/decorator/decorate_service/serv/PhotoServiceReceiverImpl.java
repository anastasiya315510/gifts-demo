package com.epam.decorator.decorate_service.serv;

import com.epam.decorator.decorate_service.controller.PhotoController;
import com.epam.decorator.decorate_service.model.Photo;

import com.epam.decorator.decorate_service.repository.PhotoRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
public class PhotoServiceReceiverImpl implements PhotoServiceReceiver {

    private static final Logger logger = LoggerFactory.getLogger(PhotoController.class);

    @Autowired
    PhotoRepository photoRepo;
    @Override
    public Photo getPhoto(String id) {
        logger.info("Method get photo by id");
       return photoRepo.findById(id).orElseThrow();

        }
}
