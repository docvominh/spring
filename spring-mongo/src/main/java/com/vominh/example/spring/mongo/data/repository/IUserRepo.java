package com.vominh.example.spring.mongo.data.repository;

import com.vominh.example.spring.mongo.data.document.UserDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface IUserRepo extends MongoRepository<UserDocument, String> {
    Optional<UserDocument> findByEmail(@Param("email") String email);

    Optional<UserDocument> findByUserId(@Param("userId") Integer userId);
}
