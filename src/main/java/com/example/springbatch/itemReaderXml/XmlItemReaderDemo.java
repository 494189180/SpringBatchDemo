package com.example.springbatch.itemReaderXml;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.xml.StaxEventItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.oxm.xstream.XStreamMarshaller;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableBatchProcessing
public class XmlItemReaderDemo {
    @Resource
    private JobBuilderFactory jobBuilderFactory;
    @Resource
    private StepBuilderFactory stepBuilderFactory;
    @Resource(name = "xmlFileWriter")
    private ItemWriter<? super Customer> xmlFileWriter;

    @Bean
    public Job xmlItemReaderDemoJob() {
        return jobBuilderFactory.get("xmlItemReaderDemoJob")
                .start(xmlItemReaderStep())
                .build();
    }

    @Bean
    public Step xmlItemReaderStep() {
        return stepBuilderFactory.get("xmlItemReaderStep")
                .<Customer, Customer>chunk(1)
                .reader(xmlFileReader())
                .writer(xmlFileWriter)
                .build();

    }

    @Bean
    public StaxEventItemReader<Customer> xmlFileReader() {
        StaxEventItemReader<Customer> reader = new StaxEventItemReader<Customer>();
        reader.setResource(new ClassPathResource("customer.xml"));
        //指定需要处理的标签
        reader.setFragmentRootElementName("customer");
        //把xml转换成customer对象
        XStreamMarshaller xStreamMarshaller = new XStreamMarshaller();

        Map<String, Class> map = new HashMap<>();
        map.put("customer", Customer.class);
        xStreamMarshaller.setAliases(map);

        reader.setUnmarshaller(xStreamMarshaller);
        return reader;
    }

}
