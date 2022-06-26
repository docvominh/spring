package com.vominh.example.spring.batch.jobs;

import com.vominh.example.spring.batch.config.JobCompletionNotificationListener;
import com.vominh.example.spring.batch.tasks.Processor;
import com.vominh.example.spring.batch.tasks.Reader;
import com.vominh.example.spring.batch.tasks.Writer;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.context.annotation.Bean;

@EnableBatchProcessing
public class SimpleJob {
    public final JobBuilderFactory jobBuilderFactory;
    public final StepBuilderFactory stepBuilderFactory;

    public SimpleJob(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;

    }

    @Bean
    public Job readAndProcessAndWriteJob(JobCompletionNotificationListener listener) {


        var r = new Reader();
        var p = new Processor();
        var w = new Writer();

        var step1 = stepBuilderFactory.get("step1").chunk(10)
                .reader(r)
                .processor(p)
                .writer(w)
                .build();

//        var step2 = stepBuilderFactory.get("step2").chunk(10).build();

        return jobBuilderFactory.get("readAndProcessAndWriteJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(step1)
                .end()
                .build();
    }

}
