package com.epam.decorator.decorate_service.serv;

import com.epam.decorator.decorate_service.model.EventDTO;
import com.epam.decorator.decorate_service.model.ImageResponse;

public interface PhotoDecorator {


    ImageResponse createCard(String templateId, EventDTO event);
}
