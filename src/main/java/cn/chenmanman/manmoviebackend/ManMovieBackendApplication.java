package cn.chenmanman.manmoviebackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class ManMovieBackendApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(ManMovieBackendApplication.class, args);
    }

}
