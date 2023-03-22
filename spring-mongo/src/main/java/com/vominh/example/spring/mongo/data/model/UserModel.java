package com.vominh.example.spring.mongo.data.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vominh.example.spring.mongo.data.object.Address;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class UserModel extends GenericModel{
    private Integer userId;
    private String name;
    private String email;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String rePassword;
    private List<Address> addresses;

}
