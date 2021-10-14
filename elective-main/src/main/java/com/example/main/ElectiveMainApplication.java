package com.example.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.util.StringUtils;

/**
 * 应用程序的入口，整个选课系统从这里启动
 *
 * @author admin
 * 使用@ComponentScan指定自动扫描的包，否则在其他模块下的bean无法被spring ioc容器管理
 */
@ComponentScan("com.example.*")
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
