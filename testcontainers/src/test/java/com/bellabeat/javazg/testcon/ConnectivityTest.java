package com.bellabeat.javazg.testcon;

import static org.junit.Assert.*;
import org.junit.ClassRule;
import org.junit.Test;
import org.testcontainers.containers.MySQLContainer;

/**
 * @author Marko Frankovic
 * @since 11/13/2019
 **/
public class ConnectivityTest {

    @ClassRule
    public static MySQLContainer mySQLContainer = new MySQLContainer("mysql:5.7.21");

    @Test
    public void testConnect()   {
        assertNotNull(mySQLContainer.getJdbcUrl());
        System.out.println("Jdbc url: " + mySQLContainer.getJdbcUrl());
    }
}
