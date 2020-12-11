package com.epam.decorator.decorate_service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.Binary;
import org.springframework.data.annotation.Id;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Photo {
    @Id
    private String id;

    private String title;

    private Binary image;

    public Photo(String title) {
    }
}
