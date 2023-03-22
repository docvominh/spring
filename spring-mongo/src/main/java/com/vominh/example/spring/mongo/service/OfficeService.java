package com.vominh.example.spring.mongo.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vominh.example.spring.mongo.data.document.OfficeDocument;
import com.vominh.example.spring.mongo.data.mapper.OfficeMapper;
import com.vominh.example.spring.mongo.data.model.OfficeModel;
import com.vominh.example.spring.mongo.data.repository.IOfficeRepo;
import com.vominh.example.spring.mongo.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OfficeService extends GenericService<OfficeModel, OfficeDocument, String> {

    private final IOfficeRepo repo;
    private final OfficeMapper mapper;

    public OfficeService(IOfficeRepo repo, OfficeMapper mapper, ObjectMapper objectMapper) {
        super(repo, mapper, objectMapper);
        this.repo = repo;
        this.mapper = mapper;

        modelType = objectMapper.getTypeFactory().constructCollectionType(List.class, OfficeModel.class);
    }

    public OfficeModel findByOfficeId(String officeId) {
        var opt = repo.findByOfficeId(officeId);
        if (opt.isEmpty()) {
            throw new ResourceNotFoundException(String.format("Could found office with id %s", officeId));
        }

        return mapper.documentToModel(opt.get());
    }

    public OfficeModel update(OfficeModel model) {
        var opt = repo.findByOfficeId(model.getOfficeId());

        if (opt.isEmpty()) {
            throw new ResourceNotFoundException(String.format("Could found office with id %s", model.getOfficeId()));
        }

        var document = opt.get();
        document.setOfficeId(model.getOfficeId());
        document.setName(model.getName());
        document.setAddress(model.getAddress());
        document = repo.save(document);

        return mapper.documentToModel(document);
    }

    public void delete(String officeId) {
        repo.deleteByOfficeId(officeId);
    }
}
