package com.example.springbatch.itemwriterMulti;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.LineAggregator;
import org.springframework.batch.item.support.ClassifierCompositeItemWriter;
import org.springframework.batch.item.support.CompositeItemWriter;
import org.springframework.batch.item.xml.StaxEventItemWriter;
import org.springframework.classify.Classifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.oxm.xstream.XStreamMarshaller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class MultiFileItemWriterConfig {

    //实现分类
    @Bean
    public ClassifierCompositeItemWriter<Customer> classifierMultiFileItemWriter() {
        ClassifierCompositeItemWriter<Customer> writer = new ClassifierCompositeItemWriter<Customer>();
        writer.setClassifier(new Classifier<Customer, ItemWriter<? super Customer>>() {
            @Override
            public ItemWriter<? super Customer> classify(Customer customer) {
                ItemWriter<Customer> write = null;
                try {
                    write = customer.getId() % 2 == 0 ? fileItemWriter() : xmlItemWriter();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return write;
            }
        });
        return writer;

    }

    //输出数据到多个文件
    @Bean
    public CompositeItemWriter<Customer> multiFileItemWriter() throws Exception {
        CompositeItemWriter<Customer> writer = new CompositeItemWriter<Customer>();
        writer.setDelegates(Arrays.asList(fileItemWriter(), xmlItemWriter()));
        writer.afterPropertiesSet();
        return writer;

    }


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

    @Bean
    public StaxEventItemWriter<Customer> xmlItemWriter() throws Exception {
        StaxEventItemWriter<Customer> writer = new StaxEventItemWriter<Customer>();

        XStreamMarshaller marshaller = new XStreamMarshaller();
        Map<String, Class> aliases = new HashMap<>();
        aliases.put("custmoter", Customer.class);
        marshaller.setAliases(aliases);

        writer.setRootTagName("customers");
        writer.setMarshaller(marshaller);

        String path = "f://custmoter.xml";
        writer.setResource(new FileSystemResource(path));
        writer.afterPropertiesSet();

        return writer;


    }
}
