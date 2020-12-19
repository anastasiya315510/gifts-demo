package com.epam.decorator.decorate_service.serv;

import com.epam.decorator.decorate_service.model.EventDTO;
import com.epam.decorator.decorate_service.model.ImageResponse;
import com.epam.decorator.decorate_service.model.Photo;
import com.epam.decorator.decorate_service.repository.PhotoRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Base64;


@Service
@Slf4j
public class PhotoDecoratorImpl implements PhotoDecorator, Closeable{
    @Value("${name}")
    private String name;

    @Autowired
    PhotoRepository photoRepository;

    @Override
    public ImageResponse createCard(String templateId, EventDTO event) {
        try {
            log.info("enter to createCard ");
            Photo photo = photoRepository.findById(templateId).orElseThrow();
            String encode = Base64.getEncoder().encodeToString(photo.getImage().getData());
            byte[] decodedBytes = Base64.getDecoder().decode(encode);
            name = name + templateId + ".jpg";
            FileUtils.writeByteArrayToFile(new File(name), decodedBytes);
            File file = new File(name);
            BufferedImage image =createImage(file, event);

            ImageIO.write(image, "jpg", new File(name));
            byte[] bytes = sendToDataBase(image);
            log.info("method Input stream = sendToDataBase");
            return ImageResponse.builder().image(bytes).self("self").build();
        } catch (IOException e) {
            log.error("error occurred " + e.getMessage());
        }
        return ImageResponse.builder().build();
    }

    private byte[] sendToDataBase(BufferedImage image) {

        try(ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            System.err.println("DataBase");
            log.info("enter to sendData Base ");
            ImageIO.write(image, "jpg", os);
            log.info("write image sendToDataBase ");
            byte[] bytes = os.toByteArray();
            log.info("convert To Bytes sendToDataBase ");
            System.out.println("!!!"+bytes);
            InputStream is = new ByteArrayInputStream(bytes);
            log.info("create InputStream sendToDataBase ");
            Photo photo = new Photo();
            photo.setImage(
                    new Binary(BsonBinarySubType.BINARY, bytes));
            log.info("set InputStream To Photo  sendToDataBase ");
            photoRepository.insert(photo);
            log.info("insert To DataBase sendToDataBase ");
            return bytes;


        } catch (IOException e) {
            log.error("error occurred " + e.getMessage());
        }

        return null;
    }

    private BufferedImage createImage(File file, EventDTO event) throws IOException {

        BufferedImage image =ImageIO.read(file);


        Graphics g = image.getGraphics();
        Graphics2D g2d = (Graphics2D) g;
        Font font = new Font("Alexander", Font.BOLD, 46);
        g2d.setFont(font);
        g2d.setColor(Color.WHITE);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
        FontMetrics metrics = g2d.getFontMetrics(font);
        g2d.drawString(event.getTitle(), 60, 100);
        g2d.drawString(event.getDescription(), 60, 150);
        if(event.getEventTime()==null){
            g2d.drawString("at 15.00", 60, 200);
        }else{
        g2d.drawString(event.getEventTime().toString(), 60, 200);
        }
        g2d.drawString(event.getEventDate().toString(), 60, 250);
        g2d.dispose();

         return image;
    }

    @Override
    public void close() throws IOException {
        System.out.println("Resource was close");
    }
}

