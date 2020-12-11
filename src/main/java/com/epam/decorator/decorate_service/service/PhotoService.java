package com.epam.decorator.decorate_service.service;



import com.epam.decorator.decorate_service.controller.PhotoController;
import com.epam.decorator.decorate_service.model.Photo;
import com.epam.decorator.decorate_service.model.Response;
import com.epam.decorator.decorate_service.repository.PhotoRepository;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service
public class PhotoService implements PhotoServiceHandler {
    private static final Logger logger = LoggerFactory.getLogger(PhotoController.class);

    @Value("${name}")
    private String name;



    @Autowired
    private PhotoRepository photoRepo;



    public String addPhoto(String title, MultipartFile file){

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

        return photo.getTitle();
    }


    @Override
    public Response getPhoto(String id, Model model) {
        
            Photo photo = photoRepo.findById(id).get();

           try{
               log.info("enter to getPhoto photo from database");
               return getResponse(id, photo);

           } catch (IOException e) {
           logger.info("error occured get photo from database"+e.getMessage());
        }
        return Response.builder().wasCreated(false).build();
    }

    private Response getResponse(String id, Photo photo) throws IOException {
        String encode = Base64.getEncoder().encodeToString(photo.getImage().getData());
        byte[] decodedBytes = Base64.getDecoder().decode(encode);
        name = name + id + ".jpg";
        System.out.println(name);
        FileUtils.writeByteArrayToFile(new File(name), decodedBytes);
        return Response.builder().wasCreated(true).build();
    }

    @Override
    public Response createCard(String text) {
        try {
            log.info("enter to createCard ");
            File file = new File(name);
            BufferedImage image = ImageIO.read(file);

            Graphics g = image.getGraphics();
            Graphics2D g2d = (Graphics2D) g;
            Font font = new Font("Alexander", Font.BOLD, 46);
            g2d.setFont(font);
            g2d.setColor(Color.WHITE);

            g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                    RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
            FontMetrics metrics = g2d.getFontMetrics(font);
            g2d.drawString(text, 260, 150);
            g2d.dispose();
            ImageIO.write(image, "jpg", new File(name));
            return Response.builder().wasCreated(true).build();
        }catch (IOException e){
            logger.info("error occurred "+e.getMessage());
        }
        return Response.builder().wasCreated(false).build();
    }

    @Override
    public String addPhoto(String title, MultipartFile file, Model model) {

        Photo photo = new Photo(title);
        try {
            photo.setImage(new Binary(BsonBinarySubType.BINARY, file.getBytes()));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        photo = photoRepo.insert(photo);
        return photo.getId();

    }

    @Override
    public List<Photo> getAll() {
        return new ArrayList<Photo>();
    }


}
