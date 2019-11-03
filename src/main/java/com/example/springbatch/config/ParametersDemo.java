package com.example.springbatch.config;

import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;

import javax.annotation.Resource;
import java.util.Map;

//@Configuration
//@EnableBatchProcessing
public class ParametersDemo implements StepExecutionListener {
    @Resource
    private JobBuilderFactory jobBuilderFactory;
    @Resource
    private StepBuilderFactory stepBuilderFactory;

    private Map<String, JobParameter> parameters;

    @Bean
    public Job parameterJob() {
        return jobBuilderFactory.get("parameterJob")
                .start(step1())
                .build();
    }

    /**
     * Job执行的是step，Job使用的数据肯定也是step的
     * 那我们只需要给step传递数据，怎么给step传递数据呢
     * 使用监听，使用Step级别的监听来传递数据
     *
     * @return
     */
    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
                .listener(this)
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
                        //输出接收到的参数
                        System.out.println(parameters.get("info"));
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
