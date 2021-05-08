package com.careerzip.testconfig.base;

import com.careerzip.config.jpa.JpaConfig;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@AutoConfigureTestDatabase
@ActiveProfiles("test")
@Import({JpaConfig.class})
@DataJpaTest
public class BaseRepositoryTest {

}
