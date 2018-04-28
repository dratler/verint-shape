package com.verint;
import com.verint.mock.DataSource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.verint.service",
                "com.verint.datasource",
                "com.verint.contoller",
                "com.verint.mock"})
public class Application {
    public static void main(String[] args) throws Exception {

        SpringApplication.run(Application.class, args);
    }
}
