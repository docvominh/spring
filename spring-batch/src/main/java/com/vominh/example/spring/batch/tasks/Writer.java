package com.vominh.example.spring.batch.tasks;

import com.vominh.example.spring.batch.dto.PopulationDto;
import org.springframework.batch.item.ItemWriter;

import java.util.List;

public class Writer<PopulationDto> implements ItemWriter<PopulationDto> {

    @Override
    public void write(List<? extends PopulationDto> list) throws Exception {

    }
}
