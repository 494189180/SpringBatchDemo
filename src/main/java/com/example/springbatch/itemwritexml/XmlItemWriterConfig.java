package com.example.springbatch.itemwritexml;

import org.springframework.batch.item.xml.StaxEventItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.oxm.xstream.XStreamMarshaller;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class XmlItemWriterConfig {

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
