package com.epam.decorator.decorate_service.controller;


import com.epam.decorator.decorate_service.model.EventDTO;
import com.epam.decorator.decorate_service.model.ImageResponse;
import com.epam.decorator.decorate_service.model.Photo;
import com.epam.decorator.decorate_service.serv.PhotoDecorator;
import com.epam.decorator.decorate_service.serv.PhotoServiceCreator;
import com.epam.decorator.decorate_service.serv.PhotoServiceReceiver;
import lombok.SneakyThrows;
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
    PhotoServiceReceiver photoServiceReceiver;


    @Autowired
    PhotoServiceCreator photoServiceCreator;

    @Autowired
    PhotoDecorator photoDecorator;



    @PostMapping("/photos/add")
    public String addPhoto(@RequestParam String title,
                           @RequestBody MultipartFile file)
            throws IOException {
        log.info(" user was entered in  /photos/add endpoint");
        if (title == null || file.isEmpty()) {
            logger.error(" PhotoController, addPhoto() -incorrect file or title was missed");
            return "not found";
        }

        return photoServiceCreator.addPhoto(title, file);

    }


    @GetMapping("/photos/{id}")
    public Photo getPhoto(@PathVariable String id) {
        log.info(" user was entered in  /photos/{id} endpoint");
        if (id == null) {
            logger.error(" PhotoController, getPhoto() -incorrect value there is no title");

        }
        return photoServiceReceiver.getPhoto(id);

    }

    @SneakyThrows
    @PostMapping("/{templateId}")
    public ImageResponse createCard(@PathVariable String templateId,
                                    @RequestBody EventDTO event) {
        log.info(" user was entered in  /{text} endpoint");
        if (event == null) {
            logger.error("Error was happend PhotoController, createCard() -incorrect value text not found");

        }
        return photoDecorator.createCard(templateId, event);
    }


}

