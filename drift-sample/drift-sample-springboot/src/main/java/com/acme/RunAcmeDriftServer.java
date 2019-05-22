package com.acme;

import com.acme.AcmeDriftConfig;
import org.springframework.boot.SpringApplication;

public class RunAcmeDriftServer {

    public static void main(String[] args) {

        SpringApplication.run(AcmeDriftConfig.class, args);

    }

}
