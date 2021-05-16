package com.careerzip.testconfig.base;

import com.careerzip.config.jpa.JpaConfig;
import com.careerzip.testconfig.jpa.TestQuerydslConfig;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@AutoConfigureTestDatabase
@ActiveProfiles("test")
@Import({JpaConfig.class, TestQuerydslConfig.class})
@DataJpaTest
public class BaseRepositoryTest {

}
