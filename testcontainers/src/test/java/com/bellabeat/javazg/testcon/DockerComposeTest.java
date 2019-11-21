package com.bellabeat.javazg.testcon;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.RestTemplate;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.containers.wait.strategy.WaitAllStrategy;
import org.testcontainers.containers.wait.strategy.WaitStrategyTarget;

import java.io.File;

/**
 * @author Marko Frankovic
 * @since 11/13/2019
 **/
public class DockerComposeTest {

    @Rule
    public DockerComposeContainer dockerComposeContainer = new DockerComposeContainer(new File("docker-compose.yaml"))
            .withExposedService("app",8080)
            .withLocalCompose(true)
            .waitingFor("app", new WaitAllStrategy()
                    .withStrategy(Wait.forHttp("/actuator/info").forStatusCode(200))
                    .withStrategy(Wait.forListeningPort())
            );

    @Test
    public void testConnect()   {
        final Integer servicePort = dockerComposeContainer.getServicePort("app", 8080);
        final RestTemplate restTemplate = new RestTemplateBuilder().rootUri("http://localhost:" + servicePort).build();
        restTemplate.put("/users", User.builder().id("pero123").build());
        Assert.assertEquals("pero123", restTemplate.getForObject("/users/pero123", User.class).getId());
    }
}
