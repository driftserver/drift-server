package com.github.driftserver.ui;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class RunFrontend {
  public static void main(String[] args) throws Exception {
    new SpringApplicationBuilder()
      .sources(RunFrontend.class)
      .run(args);
  }

}
