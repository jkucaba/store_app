package jkucaba.springstore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SpringStoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringStoreApplication.class, args);
    }

}
