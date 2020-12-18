package com.epam.decorator.decorate_service.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

@Data
@Builder
public class ImageResponceDTO {
    private BufferedImage image;
    private String self;
}
