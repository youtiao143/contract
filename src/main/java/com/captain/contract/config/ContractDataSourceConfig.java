package com.captain.contract.config;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

/**
 * @author : liuguang
 * @date : 2018/9/13 15:21
 * @escription : contract 数据源配置
 */
@Configuration
@MapperScan(basePackages = "com.captain.mapper.contract",sqlSessionFactoryRef = "contractSqlSessionFactory")
public class ContractDataSourceConfig {

    @Primary
    @Bean(name = "contractDataSource")
    @ConfigurationProperties("spring.datasource.druid.contract")
    public DataSource contractDataSource(){
        return DruidDataSourceBuilder.create().build();
    }

    @Bean(name = "contractSqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("contractDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource);
        sessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources("classpath*:mapper/contract/*.xml"));
        return sessionFactoryBean.getObject();
    }
}
