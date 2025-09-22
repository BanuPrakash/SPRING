package com.cisco.demo.cfg;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

// only config classes can have factory methods
@Configuration
public class AppConfig {

    // @Bean informs Spring to invoke the method
    // returned object should be managed within Spring container
    @Bean
    public DataSource getDataSource() throws Exception{
        ComboPooledDataSource cpds = new ComboPooledDataSource();
        cpds.setDriverClass( "org.h2.Driver" ); //loads the jdbc driver
        cpds.setJdbcUrl( "jdbc:h2:mem:testdb" );
        cpds.setUser("sa");
        cpds.setPassword("");
        cpds.setMinPoolSize(5);
        cpds.setAcquireIncrement(5);
        cpds.setMaxPoolSize(20);
        return cpds;
    }
}
