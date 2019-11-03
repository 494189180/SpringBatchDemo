package com.example.springbatch.itemwriterMulti;

import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.support.MySqlPagingQueryProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.RowMapper;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class DbJdbcReaderConfig {
    @Resource
    private DataSource dataSource;

    @Bean
    public JdbcPagingItemReader<Customer> dbJdbcReader() {
        JdbcPagingItemReader<Customer> reader = new JdbcPagingItemReader<Customer>();
        reader.setDataSource(dataSource);
        reader.setFetchSize(2);
        //把读取的数据转成user对象
        reader.setRowMapper(new RowMapper<Customer>() {
            @Override
            public Customer mapRow(ResultSet resultSet, int i) throws SQLException {
                Customer customer = new Customer();
                customer.setId(resultSet.getLong(1));
                customer.setFirstName(resultSet.getString(2));
                customer.setLastName(resultSet.getString(3));
                customer.setBirthday(resultSet.getString(4));
                return customer;
            }
        });

        //指定sql语句
        MySqlPagingQueryProvider provider = new MySqlPagingQueryProvider();
        provider.setSelectClause("id,firstName,lastName,birthday");
        provider.setFromClause("from customer");
        //指定根据那个字段进行排序
        Map<String, Order> sort = new HashMap<>();
        sort.put("id", Order.ASCENDING);
        provider.setSortKeys(sort);

        reader.setQueryProvider(provider);
        return reader;

    }
}
