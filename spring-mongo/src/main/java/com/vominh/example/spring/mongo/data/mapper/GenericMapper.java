package com.vominh.example.spring.mongo.data.mapper;

import java.util.List;

//@Mapper
public interface GenericMapper<MODEL, DOCUMENT> {
    MODEL documentToModel(DOCUMENT document);

    DOCUMENT modelToDocument(MODEL model);

    List<MODEL> documentsToModels(List<DOCUMENT> documents);

    List<DOCUMENT> modelsToDocuments(List<MODEL> models);
}
