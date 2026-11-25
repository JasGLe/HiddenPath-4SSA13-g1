package com.hiddenpath.userreputationservice;

import org.springframework.boot.SpringApplication;

public class TestUserReputationServiceApplication {

    public static void main(String[] args) {
        SpringApplication.from(UserReputationServiceApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
