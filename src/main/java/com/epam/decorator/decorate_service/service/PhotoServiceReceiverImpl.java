package com.epam.decorator.decorate_service.service;

import com.epam.decorator.decorate_service.model.Photo;
import com.epam.decorator.decorate_service.repository.PhotoRepository;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.awt.image.BufferedImage;
import java.io.File;

@Service
public class PhotoServiceReceiverImpl implements PhotoServiceReceiver {
    @Autowired
    PhotoRepository photoRepository;
    @Override
    public  Photo getPhoto(String id) {
        return photoRepository.findById(id).orElseThrow();

    }
}
