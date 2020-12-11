package com.epam.decorator.decorate_service.repository;


import com.epam.decorator.decorate_service.model.Photo;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PhotoRepository extends MongoRepository<Photo, String> {
}
