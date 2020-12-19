package com.epam.decorator.decorate_service.model;


import lombok.Builder;
import lombok.Data;

import java.awt.image.BufferedImage;


@Data
@Builder
public class ImageResponceDTO {
    private BufferedImage image;
    private String self;
}
