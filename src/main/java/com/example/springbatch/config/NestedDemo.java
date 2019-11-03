package com.example.springbatch.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.JobStepBuilder;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.annotation.Resource;

/**
 * 嵌套job要把子job转化成step类型的job
 * 还要在配置文件里配置 spring.batch.job.names=parentJob
 */
//@Configuration
//@EnableBatchProcessing
public class NestedDemo {
    @Resource
    private JobBuilderFactory jobBuilderFactory;
    @Resource
    private StepBuilderFactory stepBuilderFactory;
    @Resource
    private Job childJobOne;
    @Resource
    private Job childJobTwo;
    @Resource
    private JobLauncher launcher;

    @Bean
    public Job parentJob(JobRepository registry, PlatformTransactionManager transactionManager) {
        return jobBuilderFactory.get("parentJob")
                .start(childJob1(registry, transactionManager))
                .next(childJob2(registry, transactionManager))
                .build();
    }

    //返回的是Job类型的Step，特殊的Step
    private Step childJob2(JobRepository registry, PlatformTransactionManager transactionManager) {
        return new JobStepBuilder(new StepBuilder("childJob2"))
                .job(childJobTwo)
                .launcher(launcher)
                .repository(registry)
                .transactionManager(transactionManager)
                .build();
    }

    private Step childJob1(JobRepository registry, PlatformTransactionManager transactionManager) {
        return new JobStepBuilder(new StepBuilder("childJob1"))
                .job(childJobOne)
                .launcher(launcher)
                .repository(registry)
                .transactionManager(transactionManager)
                .build();
    }


}
