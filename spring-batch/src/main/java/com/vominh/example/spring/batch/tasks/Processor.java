package com.vominh.example.spring.batch.tasks;

import org.springframework.batch.item.ItemProcessor;

public class Processor<String, PopulationDto> implements ItemProcessor<String, PopulationDto> {
    @Override
    public PopulationDto process(String json) throws Exception {
        return null;
    }
}
