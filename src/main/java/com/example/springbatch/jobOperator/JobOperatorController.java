package com.example.springbatch.jobOperator;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobInstanceAlreadyExistsException;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class JobOperatorController {

    @Resource(name = "jobOperatorDemoJob")
    private Job jobOperatorDemoJob;
    @Resource
    private JobOperator jobOperator;

    @RequestMapping("/job2/{msg}")
    public String jobRun2(@PathVariable String msg) throws JobParametersInvalidException, JobInstanceAlreadyExistsException, NoSuchJobException {
        //启动任务同时传参
        jobOperator.start("jobOperatorDemoJob", "msg=" + msg);
        return "job success";

    }
}
