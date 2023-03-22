package com.vominh.example.spring.mongo.data.mapper;

import com.vominh.example.spring.mongo.data.document.OfficeDocument;
import com.vominh.example.spring.mongo.data.model.OfficeModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OfficeMapper extends GenericMapper<OfficeModel, OfficeDocument> {
}
