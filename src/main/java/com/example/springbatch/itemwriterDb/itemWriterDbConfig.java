package com.example.springbatch.itemwriterDb;

import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import javax.sql.DataSource;


@Configuration
public class itemWriterDbConfig {
    @Resource
    private DataSource dataSource;

    @Bean
    public JdbcBatchItemWriter<Customer> itemWriterDb() {
        JdbcBatchItemWriter<Customer> writer = new JdbcBatchItemWriter<Customer>();
        writer.setDataSource(dataSource);
        writer.setSql("insert into customer(id,firstName,lastName,birthday) values " +
                "(:id,:firstName,:lastName,:birthday)");
        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());

        return writer;
    }

}
