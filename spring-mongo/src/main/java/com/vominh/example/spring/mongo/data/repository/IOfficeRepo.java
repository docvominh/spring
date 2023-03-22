package com.vominh.example.spring.mongo.data.repository;

import com.vominh.example.spring.mongo.data.document.OfficeDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface IOfficeRepo extends MongoRepository<OfficeDocument, String> {
    Optional<OfficeDocument> findByOfficeId(@Param("officeId") String officeId);

    void deleteByOfficeId(@Param("officeId") String officeId);
}
