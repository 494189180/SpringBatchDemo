package com.example.springbatch.config;

import com.example.springbatch.listener.MyChunkListener;
import com.example.springbatch.listener.MyJobListener;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.context.annotation.Bean;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * step有两个实现方式一个是chunk，另一个是tasklet
 */
//@Configuration
//@EnableBatchProcessing
public class ListenerDemo {
    @Resource
    private JobBuilderFactory jobBuilderFactory;
    @Resource
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job listenerJob() {
        return jobBuilderFactory.get("listenerJob")
                .start(step1())
                .listener(new MyJobListener())
                .build();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
                .<String, String>chunk(2)
                .faultTolerant()
                .listener(new MyChunkListener())
                .reader(read())
                .writer(write())
                .build();
    }

    @Bean
    public ItemWriter<String> write() {
        return new ItemWriter<String>() {
            @Override
            public void write(List<? extends String> list) throws Exception {
                for (String item : list) {
                    System.out.println(item);
                }
            }
        };
    }

    @Bean
    public ItemReader<String> read() {
        return new ListItemReader<>(Arrays.asList("java", "spring", "springbatch"));
    }
}
