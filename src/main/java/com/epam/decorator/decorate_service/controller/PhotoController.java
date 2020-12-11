package com.epam.decorator.decorate_service.controller;



import com.epam.decorator.decorate_service.model.Photo;
import com.epam.decorator.decorate_service.model.Response;
import com.epam.decorator.decorate_service.service.PhotoServiceHandler;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
public class PhotoController {
    private static final Logger logger = LoggerFactory.getLogger(PhotoController.class);

    @Autowired
    PhotoServiceHandler photoServiceHandler;


    @PostMapping("/photos/add")
    public String addPhoto(@RequestParam String title,
                           @RequestBody MultipartFile file, Model model)
            throws IOException {
        log.info(" user was entered in  /photos/add endpoint");
        if(title==null|| file.isEmpty()){
            logger.error(" PhotoController, addPhoto() -incorrect file or title was missed");
            return "not found";
        }

       return   photoServiceHandler.addPhoto(title, file, model);

    }


    @GetMapping("/photos/{id}")
    public Response getPhoto(@PathVariable String id, Model model) {
        log.info(" user was entered in  /photos/{id} endpoint");
        if(id==null){
            logger.error(" PhotoController, getPhoto() -incorrect value there is no title");
            return Response.builder().wasCreated(false).build();

        }
    return  photoServiceHandler.getPhoto(id, model);

    }

    @SneakyThrows
    @PostMapping("/{text}")
    public Response createCard(@PathVariable String text) {
        log.info(" user was entered in  /{text} endpoint");
        if(text==null){
            logger.error("Error was happend PhotoController, createCard() -incorrect value text not found");
            return Response.builder().wasCreated(false).build();
        }
        return photoServiceHandler.createCard(text);
    }

    @GetMapping("/all")
    public List<Photo> getAll(){
        return photoServiceHandler.getAll();
    }


}

