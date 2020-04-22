package top.runok;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@SpringBootApplication
public class DemoApplication  implements WebMvcConfigurer{

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}


