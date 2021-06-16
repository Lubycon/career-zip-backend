package com.careerzip;

import com.careerzip.global.jwt.JwtProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

import static com.careerzip.global.jpa.JpaProperties.Seoul;

@EnableConfigurationProperties({JwtProperties.class})
@SpringBootApplication
public class CareerZipApplication {

    @PostConstruct
    public void setTimeZone() {
        TimeZone.setDefault(TimeZone.getTimeZone(Seoul));
    }

    public static void main(String[] args) {
        SpringApplication.run(CareerZipApplication.class, args);
    }

}
