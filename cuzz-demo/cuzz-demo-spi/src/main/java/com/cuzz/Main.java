package com.cuzz;

import java.util.Iterator;
import java.util.ServiceLoader;

public class Main {
    public static void main(String[] args) {
        // Java spi 机制
        ServiceLoader<Log> serviceLoader =
                ServiceLoader.load(Log.class);
        Iterator<Log> iterator = serviceLoader.iterator();
        while (iterator.hasNext()) {
            Log log = iterator.next();
            log.log("JDK SPI");
        }
    }
}
