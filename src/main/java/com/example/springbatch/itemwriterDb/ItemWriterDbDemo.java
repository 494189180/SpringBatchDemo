package com.example.springbatch.itemwriterDb;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing
public class ItemWriterDbDemo {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    @Autowired
    @Qualifier("flatFileReader")
    private ItemReader<? extends Customer> itemReaderDb;
    @Autowired
    @Qualifier("itemWriterDb")
    private ItemWriter<? super Customer> itemWriterDb;

    @Bean
    public Job ItemWriterDbDemoJob() {
        return jobBuilderFactory.get("ItemWriterDbDemoJob")
                .start(itemWriterDbDemoStep())
                .build();
    }

    @Bean
    public Step itemWriterDbDemoStep() {
        return stepBuilderFactory.get("itemWriterDbDemo")
                .<Customer, Customer>chunk(1)
                .reader(itemReaderDb)
                .writer(itemWriterDb)
                .build();
    }


}














