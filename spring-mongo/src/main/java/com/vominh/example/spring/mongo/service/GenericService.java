package com.vominh.example.spring.mongo.service;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.vominh.example.spring.mongo.data.mapper.GenericMapper;
import com.vominh.example.spring.mongo.data.model.GenericModel;
import com.vominh.example.spring.mongo.exception.ResourceNotFoundException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.util.List;

public class GenericService<MODEL extends GenericModel, DOCUMENT, ID extends String> {
    private final MongoRepository<DOCUMENT, ID> repository;
    private final GenericMapper<MODEL, DOCUMENT> mapper;
    private final ObjectMapper objectMapper;
    protected JavaType modelType;

    public GenericService(MongoRepository<DOCUMENT, ID> repository, GenericMapper<MODEL, DOCUMENT> mapper, ObjectMapper objectMapper) {
        this.repository = repository;
        this.mapper = mapper;
        this.objectMapper = objectMapper;
    }

    public MODEL findById(ID id) {
        var opt = repository.findById(id);
        if (opt.isEmpty()) {
            throw new ResourceNotFoundException(String.format("Couldn't found document with id %s", id));
        }

        var document = opt.get();

        return mapper.documentToModel(document);
    }

    public MODEL save(MODEL model) {
        var document = mapper.modelToDocument(model);
        document = repository.save(document);
        return mapper.documentToModel(document);
    }

    public MODEL update(MODEL model) {
        var opt = repository.findById((ID) model.getDocId());

        if (opt.isEmpty()) {
            throw new ResourceNotFoundException(String.format("Couldn't found document with id %s", model.getDocId()));
        }

        var document = mapper.modelToDocument(model);
        document = repository.save(document);

        return mapper.documentToModel(document);
    }

    public void delete(ID id) {
        repository.deleteById(id);
    }

    public byte[] export() throws IOException {
        var documents = repository.findAll();
        var models = mapper.documentsToModels(documents);
        ObjectWriter writer = objectMapper.writer(new DefaultPrettyPrinter());
        String home = System.getProperty("user.home");
        var temp = new File(home + File.separator + "temp" + File.separator + System.currentTimeMillis());
        var result = temp.createNewFile();
        if (result) {
            writer.writeValue(temp, models);
            return Files.readAllBytes(temp.toPath());
        }

        return null;

    }

    public int importFromFile(MultipartFile file) throws IOException {
        String temp;
        var json = new StringBuilder();

        try (var bufferReader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            while ((temp = bufferReader.readLine()) != null) {
                json.append(temp);
            }
        } finally {
            file.getInputStream().close();
        }


        if (StringUtils.isNotEmpty(json.toString())) {
            var models = (List<MODEL>) objectMapper.readValue(json.toString(), modelType);
            if (!models.isEmpty()) {
                repository.deleteAll();
                var documents = mapper.modelsToDocuments(models);
                repository.saveAll(documents);

                return documents.size();
            }
        }

        return 0;
    }
}
