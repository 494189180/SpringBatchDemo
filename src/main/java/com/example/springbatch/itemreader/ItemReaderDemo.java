package com.example.springbatch.itemreader;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.context.annotation.Bean;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

//@Configuration
//@EnableBatchProcessing
public class ItemReaderDemo {
    @Resource
    private JobBuilderFactory jobBuilderFactory;
    @Resource
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job ItemReaderDemoJob() {
        return jobBuilderFactory.get("ItemReaderDemoJob")
                .start(itemReaderStep())
                .build();
    }

    @Bean
    public Step itemReaderStep() {
        return stepBuilderFactory.get("itemReaderStep")
                .<String, String>chunk(2)
                .reader(itemReaderDemoRead())
                .writer(list -> {
                    for (String str : list) {
                        System.out.println(str + "...");
                    }
                }).build();
    }

    private MyReader itemReaderDemoRead() {
        List<String> list = Arrays.asList("java", "spring", "mybatis");
        return new MyReader(list);

    }


}
