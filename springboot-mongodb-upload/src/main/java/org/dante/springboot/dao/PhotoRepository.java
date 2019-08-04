package org.dante.springboot.dao;

import org.dante.springboot.po.Photo;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PhotoRepository extends MongoRepository<Photo, String> { }

