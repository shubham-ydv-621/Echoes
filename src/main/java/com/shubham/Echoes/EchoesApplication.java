package com.shubham.Echoes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

// Disable MongoDB auto-configuration so app can run without DB
@SpringBootApplication(
        exclude = {
                org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration.class,
                org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration.class
        }
)
@EnableTransactionManagement
@EnableScheduling
public class EchoesApplication {

    public static void main(String[] args) {
        SpringApplication.run(EchoesApplication.class, args);
    }

    /*
    // Commented out MongoDB transaction manager
    @Bean
    public PlatformTransactionManager add(MongoDatabaseFactory dbFactory){
        return new MongoTransactionManager(dbFactory);
    }
    */
}
