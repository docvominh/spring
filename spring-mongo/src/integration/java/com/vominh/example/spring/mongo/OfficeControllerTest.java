package com.vominh.example.spring.mongo;

import com.vominh.example.spring.mongo.api.OfficeApi;
import com.vominh.example.spring.mongo.data.model.OfficeModel;
import com.vominh.example.spring.mongo.data.repository.IOfficeRepo;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("integration")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class OfficeControllerTest {

    @Autowired
    IOfficeRepo repo;

    @Autowired
    OfficeApi api;

    @BeforeAll
    void setup() {
        repo.deleteAll();
    }

    @Test
    @Order(0)
    void createOfficeTest() {
        var office = OfficeModel.builder()
                .officeId("VN")
                .name("Viet Nam")
                .address("42 Ngô Quang Huy, Thảo Điền, Quận 2, Thành phố Hồ Chí Minh")
                .build();
        var response = api.create(office);

        Assertions.assertEquals("Viet Nam", response.getBody().getName());
    }

    @Test
    @Order(1)
    void getOfficeTest() {
        var office = api.get("VN");
        Assertions.assertEquals("Viet Nam", office.getBody().getName());
    }

    @Test
    @Order(2)
    void updateOfficeTest() {
        var office = OfficeModel.builder()
                .officeId("VN")
                .name("Viet Nam")
                .address("382 Nui Thanh")
                .build();
        var response = api.update(office);

        Assertions.assertEquals("382 Nui Thanh", response.getBody().getAddress());
    }

    @Test
    @Order(3)
    void deleteOfficeTest() {
        var office = api.delete("VN");
        Assertions.assertEquals("deleted", office.getBody());
    }
}
