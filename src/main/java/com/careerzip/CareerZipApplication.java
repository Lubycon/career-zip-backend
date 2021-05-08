package com.careerzip;

import com.careerzip.global.jwt.JwtProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties({JwtProperties.class})
@SpringBootApplication
public class CareerZipApplication {

    public static void main(String[] args) {
        SpringApplication.run(CareerZipApplication.class, args);
    }

}
