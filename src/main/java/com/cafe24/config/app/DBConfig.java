package com.cafe24.config.app;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class DBConfig {
	
	@Bean
	public DataSource basicDataSource() {
		BasicDataSource basicDataSource = new BasicDataSource();
		basicDataSource.setDriverClassName("org.mariadb.jdbc.Driver");
		basicDataSource.setUrl("jdbc:mariadb://192.168.1.211:3307/webdb");
		basicDataSource.setUsername("webdb");
		basicDataSource.setPassword("webdb");
		basicDataSource.setInitialSize(10);//처음에 연결 얼마나
		basicDataSource.setMaxActive(100);//100개가 동적으로
		
		return basicDataSource;
	}
	
	@Bean //spring이 서비스 단에서 transaction 기능 제공해준다 중간에 오류나면 rollback
	public PlatformTransactionManager transactionManager(DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}
	
}
