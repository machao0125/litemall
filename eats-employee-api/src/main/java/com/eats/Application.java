package com.eats;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(scanBasePackages={"org.linlinjava.litemall.core","com.eats", "org.linlinjava.litemall.db"})
@MapperScan("org.linlinjava.litemall.db.dao")
@EnableTransactionManagement
//@EnableScheduling
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}