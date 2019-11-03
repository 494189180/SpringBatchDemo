package com.example.springbatch.joblauncher;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class JobLauncherController {
    @Resource
    private JobLauncher jobLauncher;
    @Resource(name = "JobLauncherDemoJob")
    private Job jobLauncherDemoJob;

    @RequestMapping("/job/{msg}")
    public String jobRun1(@PathVariable String msg) throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
        //把接收到的参数传给任务
        JobParameters parameters = new JobParametersBuilder()
                .addString("msg", msg)
                .toJobParameters();

        //启动任务并把参数传给任务
        jobLauncher.run(jobLauncherDemoJob, parameters);
        return "job success";
    }

}
