package com.example.springbatch.itemprocessor;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class FirstNameUpperProcessor implements ItemProcessor<Customer, Customer> {

    @Override
    public Customer process(Customer customer) throws Exception {
        Customer cus = new Customer();
        cus.setId(customer.getId());
        cus.setFirstName(customer.getFirstName().toUpperCase());
        cus.setLastName(customer.getLastName());
        cus.setBirthday(customer.getBirthday());
        return cus;
    }
}
