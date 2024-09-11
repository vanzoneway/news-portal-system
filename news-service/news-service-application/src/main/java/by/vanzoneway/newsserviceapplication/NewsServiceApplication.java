package by.vanzoneway.newsserviceapplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {
        "by.vanzoneway.newsservicecore",
        "by.vanzoneway.newsserviceapplication"
})
public class NewsServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(NewsServiceApplication.class, args);
    }

}
