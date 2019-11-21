package com.bellabeat.javazg.testcon.container;

import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.containers.wait.strategy.WaitAllStrategy;

/**
 * @author Marko Frankovic
 * @since 11/13/2019
 **/
public class EurekaContainer extends GenericContainer {

    public static final String VERSION = "latest";
    public static final Integer APP_PORT = 8761;
    private static final String IMAGE_NAME = "springcloud/eureka";

    public EurekaContainer() {
        this(VERSION);
    }

    public EurekaContainer(final String version) {
        super(IMAGE_NAME + ":" + version);
        super.waitStrategy = new WaitAllStrategy()
                .withStrategy(Wait.forHttp("/info").forStatusCode(200))
                .withStrategy(Wait.forListeningPort());
    }

    @Override
    protected void configure() {
        addExposedPort(APP_PORT);
    }


}
