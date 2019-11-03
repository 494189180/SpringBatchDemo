package com.example.springbatch.itemprocessor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.LineAggregator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

@Configuration
public class FileItemWriterConfig {
    @Bean
    public FlatFileItemWriter<Customer> fileItemWriter() throws Exception {
        //把customer对象转换成字符串输出文件
        FlatFileItemWriter<Customer> writer = new FlatFileItemWriter<Customer>();
        String path = "f://customerbak.txt";
        writer.setResource(new FileSystemResource(path));
        //把customer对象转成字符串
        writer.setLineAggregator(new LineAggregator<Customer>() {
            @Override
            public String aggregate(Customer customer) {
                ObjectMapper mapper = new ObjectMapper();
                String str = null;
                try {
                    str = mapper.writeValueAsString(customer);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
                return str;
            }
        });
        //做一下检查
        writer.afterPropertiesSet();
        return writer;
    }
}
