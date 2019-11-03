package com.example.springbatch.itemReaderMulti;

import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("multiFileWriter")
public class MultiFileWriter implements ItemWriter<Customer> {
    @Override
    public void write(List<? extends Customer> list) throws Exception {
        for (Customer customer : list) {
            System.out.println(customer);
        }
    }
}
