package com.example.springbatch.itemReaderDb;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.support.MySqlPagingQueryProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.RowMapper;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

//@Configuration
//@EnableBatchProcessing
public class ItemReaderDbDemo {
    @Resource
    private JobBuilderFactory jobBuilderFactory;
    @Resource
    private StepBuilderFactory stepBuilderFactory;
    @Resource
    private DataSource dataSource;
    @Resource(name = "dbJdbcWrite")
    private ItemWriter<? super User> dbJdbcWrite;

    @Bean
    public Job ItemReaderDbJob() {
        return jobBuilderFactory.get("ItemReaderDbJob")
                .start(itemReaderStep())
                .build();
    }

    @Bean
    public Step itemReaderStep() {
        return stepBuilderFactory.get("itemReaderStep")
                .<User, User>chunk(2)
                .reader(dbJdbcReader())
                .writer(dbJdbcWrite)
                .build();
    }

    @Bean
    @StepScope //指定范围 范围之内
    public JdbcPagingItemReader<User> dbJdbcReader() {
        JdbcPagingItemReader<User> reader = new JdbcPagingItemReader<User>();
        reader.setDataSource(dataSource);
        reader.setFetchSize(2);
        //把读取的数据转成user对象
        reader.setRowMapper(new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet resultSet, int i) throws SQLException {
                User user = new User();
                user.setId(resultSet.getInt(1));
                user.setUsername(resultSet.getString(2));
                user.setPassword(resultSet.getString(3));
                user.setAge(resultSet.getInt(4));
                return user;
            }
        });

        //指定sql语句
        MySqlPagingQueryProvider provider = new MySqlPagingQueryProvider();
        provider.setSelectClause("id,username,password,age");
        provider.setFromClause("from user");
        //指定根据那个字段进行排序
        Map<String, Order> sort = new HashMap<>();
        sort.put("id", Order.ASCENDING);
        provider.setSortKeys(sort);

        reader.setQueryProvider(provider);
        return reader;

    }


}
