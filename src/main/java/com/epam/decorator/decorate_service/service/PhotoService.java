package com.epam.decorator.decorate_service.service;


import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.web.multipart.MultipartFile;
import com.epam.decorator.decorate_service.controller.PhotoController;
import com.epam.decorator.decorate_service.model.*;
import com.epam.decorator.decorate_service.repository.PhotoRepository;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.io.FileUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.time.LocalTime;

import java.util.Base64;


@Slf4j
@Service
public class PhotoService implements PhotoServiceHandler {
    private static final Logger logger = LoggerFactory.getLogger(PhotoController.class);

    @Value("${name}")
    private String name;



    @Autowired
    private PhotoRepository photoRepo;


    @Override
    public ImageResponceDTO getPhotoUser(EventDTO eventDTO, String templateId) {

        File file = new File(name);
        Photo photo = photoRepo.findById(templateId).get();

            String encode = Base64.getEncoder().encodeToString(photo.getImage().getData());
            byte[] decodedBytes = Base64.getDecoder().decode(encode);
            name = name + templateId + ".jpg";
            try{
                FileUtils.writeByteArrayToFile(new File(name), decodedBytes);
            File file1 = new File(name);
            BufferedImage image = ImageIO.read(file1);
            settingTextNew(eventDTO, image);
            ImageIO.write(image, "jpg", new File(name));
            String responseId = sendToDataBase(image);
          return   ImageResponceDTO.builder().image(image).self("https://localhost:8989/photos"+responseId).build();

        } catch (IOException e) {
            e.printStackTrace();
        }


        return ImageResponceDTO.builder().build();
    }

    private String sendToDataBase(BufferedImage image) {

        Photo photo = new Photo();
        try{
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            String format="jpg";
            ImageIO.write(image, format, baos);
            byte[] bytes = baos.toByteArray();
            photo.setImage(new Binary(BsonBinarySubType.BINARY, bytes));

         }catch (IOException e) {
            System.out.println(e.getMessage());
        }
        photo = photoRepo.insert(photo);
        return photo.getId();

    }


    private void settingTextNew(EventDTO eventDTO, BufferedImage image) {
        Graphics2D g2d = (Graphics2D) image.getGraphics();
        Font font = new Font("Alexander", Font.ITALIC, 46);
        g2d.setFont(font);
        g2d.setColor(Color.ORANGE);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
        FontMetrics metrics = g2d.getFontMetrics(font);
        g2d.drawString(eventDTO.getTitle(), 60, 100);
        g2d.drawString(eventDTO.getDescription(), 60, 150);
        g2d.drawString(eventDTO.getEventDate().toString(), 60, 250);
        if(eventDTO.getEventTime()==null){
            g2d.drawString(LocalTime.now().toString(), 60, 200);
        }else {
            g2d.drawString(eventDTO.getEventTime().toString(), 60, 200);
        }
        g2d.dispose();
    }




}
