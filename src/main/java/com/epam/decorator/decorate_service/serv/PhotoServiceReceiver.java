package com.epam.decorator.decorate_service.serv;

import com.epam.decorator.decorate_service.model.Photo;


public interface PhotoServiceReceiver {
    Photo getPhoto(String id);
}
