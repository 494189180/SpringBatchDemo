package com.example.springbatch.itemwritexml;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

@Configuration
@EnableBatchProcessing
public class ItemWriterXmlDemo {
    @Resource
    private JobBuilderFactory jobBuilderFactory;
    @Resource
    private StepBuilderFactory stepBuilderFactory;
    @Resource(name = "dbJdbcReader")
    private ItemReader<? extends Customer> dbJdbcReader;
    @Resource(name = "xmlItemWriter")
    private ItemWriter<? super Customer> xmlItemWriter;

    @Bean
    public Job itemWriterXmlDemoJob() {
        return jobBuilderFactory.get("itemWriterXmlDemoJob")
                .start(itemWriterXmlDemoStep())
                .build();
    }

    @Bean
    public Step itemWriterXmlDemoStep() {
        return stepBuilderFactory.get("itemWriterXmlDemoStep")
                .<Customer, Customer>chunk(1)
                .reader(dbJdbcReader)
                .writer(xmlItemWriter)
                .build();
    }

}







