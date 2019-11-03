package com.example.springbatch.itemReaderXml;

import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("xmlFileWriter")
public class XmlFileWriter implements ItemWriter<Customer> {

    @Override
    public void write(List<? extends Customer> list) throws Exception {
        for (Customer customer : list) {
            System.out.println(customer);
        }
    }
}
