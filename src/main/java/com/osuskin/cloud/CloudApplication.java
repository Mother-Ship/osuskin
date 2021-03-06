package com.osuskin.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@EnableConfigurationProperties
@EnableTransactionManagement
@SpringBootApplication
@EntityScan(basePackages = {"com.osuskin.cloud.pojo.dto"})
@EnableJpaRepositories("com.osuskin.cloud.dao")
public class CloudApplication {

    public static void main(String[] args) {
        SpringApplication.run(CloudApplication.class, args);
    }

}
