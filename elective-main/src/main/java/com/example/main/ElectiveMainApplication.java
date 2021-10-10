package com.example.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.util.StringUtils;

/**
 * @author admin
 */
@SpringBootApplication
public class ElectiveMainApplication {

    public static void main(String[] args) {
        // 使默认命令script和stacktrace失效
        String[] disabledCommands = {"--spring.shell.command.stacktrace.enabled=false",
                "--spring.shell.command.script.enabled=false"};
        String[] newArgs = StringUtils.concatenateStringArrays(args, disabledCommands);

        SpringApplication.run(ElectiveMainApplication.class, newArgs);
    }

}
