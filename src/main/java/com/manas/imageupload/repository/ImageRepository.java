
package com.manas.imageupload.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.manas.imageupload.Model.Image;

/**
 * ImageRepository
 */
public interface ImageRepository extends MongoRepository<Image, String> {

}