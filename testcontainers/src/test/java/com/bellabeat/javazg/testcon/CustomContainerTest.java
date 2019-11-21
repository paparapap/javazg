package com.bellabeat.javazg.testcon;

import com.bellabeat.javazg.testcon.container.EurekaContainer;
import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.RestTemplate;

/**
 * @author Marko Frankovic
 * @since 11/13/2019
 **/
public class CustomContainerTest {

    @ClassRule
    public static EurekaContainer eurekaContainer = new EurekaContainer();

    @Test
    public void testHealth()    {
        final RestTemplate restTemplate = new RestTemplateBuilder().rootUri("http://localhost:" + eurekaContainer.getFirstMappedPort()).build();
        Assert.assertEquals(HttpStatus.OK, restTemplate.getForEntity("/info", Void.class).getStatusCode());
    }
}
