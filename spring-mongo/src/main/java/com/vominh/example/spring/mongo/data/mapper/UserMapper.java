package com.vominh.example.spring.mongo.data.mapper;

import com.vominh.example.spring.mongo.data.document.UserDocument;
import com.vominh.example.spring.mongo.data.model.UserModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper extends GenericMapper<UserModel, UserDocument> {

}
