package com.example.springbatch.itemprocessor;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.CompositeItemProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableBatchProcessing
public class ItemProcessorDemo {
    @Resource
    private JobBuilderFactory jobBuilderFactory;
    @Resource
    private StepBuilderFactory stepBuilderFactory;
    @Resource(name = "dbJdbcReader")
    private ItemReader<Customer> dbJdbcReader;
    @Resource(name = "fileItemWriter")
    private ItemWriter<Customer> fileItemWriter;
    @Resource(name = "firstNameUpperProcessor")
    private ItemProcessor<Customer, Customer> firstNameUpperProcessor;
    @Resource(name = "idFilterProcessor")
    private ItemProcessor<Customer, Customer> IdFilterProcessor;

    @Bean
    public Job itemProcessorDemoJob() {
        return jobBuilderFactory.get("itemProcessorDemoJob")
                .start(itemProcessorDemoStep())
                .build();
    }

    @Bean
    public Step itemProcessorDemoStep() {
        return stepBuilderFactory.get("itemProcessorDemoStep")
                .<Customer, Customer>chunk(1)
                .reader(dbJdbcReader)
                //.processor(firstNameUpperProcessor)
                .processor(processor())
                .writer(fileItemWriter)
                .build();
    }

    //有多种处理方式
    @Bean
    public CompositeItemProcessor<Customer, Customer> processor() {
        CompositeItemProcessor<Customer, Customer> processor = new CompositeItemProcessor<Customer, Customer>();
        List<ItemProcessor<Customer, Customer>> delegates = new ArrayList<>();
        delegates.add(firstNameUpperProcessor);
        delegates.add(IdFilterProcessor);

        processor.setDelegates(delegates);
        return processor;

    }

}
