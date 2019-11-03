package com.example.springbatch.joblauncher;

import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.Map;

@Configuration
@EnableBatchProcessing
public class JobLauncherDemo implements StepExecutionListener {
    @Resource
    private JobBuilderFactory jobBuilderFactory;
    @Resource
    private StepBuilderFactory stepBuilderFactory;

    private Map<String, JobParameter> parameters;

    @Bean
    public Job JobLauncherDemoJob() {
        return jobBuilderFactory.get("JobLauncherDemoJob")
                .start(jobLauncherDemoStep())
                .build();
    }

    @Bean
    public Step jobLauncherDemoStep() {
        return stepBuilderFactory.get("jobLauncherDemoStep")
                .listener(this)
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
                        System.out.println(parameters.get("msg").getValue());
                        return RepeatStatus.FINISHED;
                    }
                }).build();
    }


    @Override
    public void beforeStep(StepExecution stepExecution) {
        parameters = stepExecution.getJobParameters().getParameters();
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        return null;
    }
}
