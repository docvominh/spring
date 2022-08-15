package com.vominh.example.spring.rest.repository;

import com.vominh.example.spring.rest.entity.DeviceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IDeviceRepo extends JpaRepository<DeviceEntity, Integer> {
}
