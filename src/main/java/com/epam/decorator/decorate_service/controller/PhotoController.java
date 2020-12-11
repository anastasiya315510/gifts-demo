package com.epam.decorator.decorate_service.controller;


import com.epam.decorator.decorate_service.model.*;
import com.epam.decorator.decorate_service.service.PhotoServiceSendler;
import com.epam.decorator.decorate_service.service.PhotoServiceHandler;
import com.epam.decorator.decorate_service.service.PhotoServiceReceiver;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@Slf4j
@RestController
public class PhotoController {
    private static final Logger logger = LoggerFactory.getLogger(PhotoController.class);

    @Autowired
    PhotoServiceHandler photoServiceHandler;

    @Autowired
    PhotoServiceSendler photoServiceSendler;

    @Autowired
    PhotoServiceReceiver photoReceiver;


    @PostMapping("/photos/add")
    public String addPhoto(@RequestParam String title,
                           @RequestBody MultipartFile file)
            throws IOException {
        log.info(" user was entered in  /photos/add endpoint");
        if(title==null|| file.isEmpty()){
            logger.error(" PhotoController, addPhoto() -incorrect file or title was missed");
            return "not found";
        }

       return   photoServiceSendler.addPhoto(title, file);

    }


    @GetMapping("/get/{templateId}")
    public ImageResponceDTO getPhotoUser(@RequestBody EventDTO eventdto, @PathVariable String templateId){
        if(eventdto.getTitle()==null){
            System.out.println(" Title is null");
            return ImageResponceDTO.builder().build();
        }else if(eventdto.getDescription()== null){
            System.out.println(" Description is null, please, describe your event");
            return ImageResponceDTO.builder().build();
        }else if(eventdto.getEventTime()==null){
            System.out.println(" Please, set up time for your event");
        }else if(eventdto.getEventDate()==null){
            System.out.println(" Please, set up data for your event");
            return ImageResponceDTO.builder().build();
        }


        return photoServiceHandler.getPhotoUser(eventdto, templateId);

    }


    @GetMapping("/photos/{id}")
    public Photo getPhoto(@PathVariable String id) {
        log.info(" user was entered in  /photos/{id} endpoint");
        if(id==null){
            logger.error(" PhotoController, getPhoto() -incorrect value there is no title");


        }

        return  photoReceiver.getPhoto(id);

    }




}

