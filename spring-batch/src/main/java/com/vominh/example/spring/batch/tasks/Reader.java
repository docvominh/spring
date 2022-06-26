package com.vominh.example.spring.batch.tasks;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

public class Reader<String> implements ItemReader<String> {
    //https://datausa.io/api/data?drilldowns=Nation&measures=Population
    @Override
    public String read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        return null;
    }
}
