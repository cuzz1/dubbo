package com.cuzz;


import com.cuzz.api.Driver;

import java.util.ServiceLoader;

public class Main {
    public static void main(String[] args) {
        // Java spi 机制
        ServiceLoader<Driver> serviceLoader = ServiceLoader.load(Driver.class);
        System.out.println(serviceLoader);
        for (Driver driver : serviceLoader) {
            driver.connect("localhost:3306");
        }
    }

}
