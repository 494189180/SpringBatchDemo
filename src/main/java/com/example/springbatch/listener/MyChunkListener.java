package com.example.springbatch.listener;

import org.springframework.batch.core.annotation.AfterChunk;
import org.springframework.batch.core.annotation.BeforeChunk;
import org.springframework.batch.core.scope.context.ChunkContext;

public class MyChunkListener {
    @BeforeChunk
    public void beforeChunk(ChunkContext chunkContext) {
        System.out.println(chunkContext.getStepContext().getJobName() + "before...");
    }

    @AfterChunk
    public void afterChunk(ChunkContext chunkContext) {
        System.out.println(chunkContext.getStepContext().getJobName() + "after...");
    }
}
