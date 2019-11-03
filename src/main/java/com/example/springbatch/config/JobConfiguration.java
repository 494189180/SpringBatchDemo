package com.example.springbatch.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;

import javax.annotation.Resource;

//@Configuration
//@EnableBatchProcessing
public class JobConfiguration {
    //注入创建任务对象的对象
    @Resource
    private JobBuilderFactory jobBuilderFactory;

    //任务的执行有step决定
    //注入创建step对象的对象
    @Resource
    private StepBuilderFactory stepBuilderFactory;

    //创建任务对象
    @Bean
    public Job helloWorldJob() {
        return jobBuilderFactory.get("helloWorldJob").start(step1()).build();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1").tasklet(new Tasklet() {
            @Override
            public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
                System.out.println("hello world!");
                return RepeatStatus.FINISHED;
            }
        }).build();
    }


}
