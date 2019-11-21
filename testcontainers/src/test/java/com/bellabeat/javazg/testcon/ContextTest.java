package com.bellabeat.javazg.testcon;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.MySQLContainer;

/**
 * @author Marko Frankovic
 * @since 11/13/2019
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestconApplication.class)
@ContextConfiguration(initializers = {ContextTest.Initializer.class})
public class ContextTest {

    @ClassRule
    public static MySQLContainer mySQLContainer = new MySQLContainer()
            .withUsername("test")
            .withPassword("test")
            .withDatabaseName("testcont");

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testContextUp() {
        Assert.assertNotNull(userRepository);
    }

    @Test
    public void testPersist()   {
        User user = User.builder().id("user1234541").build();
        userRepository.save(user);
        Assert.assertNotNull(userRepository.findById("user1234541"));
    }

    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        @Override
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues
                    .of(
                            "spring.datasource.url=" + mySQLContainer.getJdbcUrl(),
                            "spring.datasource.username=test",
                            "spring.datasource.password=test",
                            "spring.flyway.url=" + mySQLContainer.getJdbcUrl(),
                            "spring.flyway.user=test",
                            "spring.flyway.password=test"
                            )
                    .applyTo(configurableApplicationContext.getEnvironment());
        }
    }
}
