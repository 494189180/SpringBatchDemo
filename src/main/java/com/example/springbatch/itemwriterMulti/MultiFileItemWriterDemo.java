package com.example.springbatch.itemwriterMulti;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemStreamWriter;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

@Configuration
@EnableBatchProcessing
public class MultiFileItemWriterDemo {
    @Resource
    private JobBuilderFactory jobBuilderFactory;
    @Resource
    private StepBuilderFactory stepBuilderFactory;
    @Resource(name = "dbJdbcReader")
    private ItemReader<? extends Customer> dbJdbcReader;
    @Resource(name = "classifierMultiFileItemWriter")
    private ItemWriter<? super Customer> multiFileItemWriter;

    @Resource(name = "fileItemWriter")
    private ItemStreamWriter<Customer> fileItemWriter;
    @Resource(name = "xmlItemWriter")
    private ItemStreamWriter<Customer> xmlItemWriter;

    @Bean
    public Job MultiFileItemWriterDemoJob() {
        return jobBuilderFactory.get("MultiFileItemWriterDemoJob1")
                .start(multiFileItemWriterDemoStep())
                .build();
    }

    @Bean
    public Step multiFileItemWriterDemoStep() {
        return stepBuilderFactory.get("multiFileItemWriterDemo")
                .<Customer, Customer>chunk(1)
                .reader(dbJdbcReader)
                .writer(multiFileItemWriter)
                .stream(fileItemWriter)
                .stream(xmlItemWriter)
                .build();
    }

}
