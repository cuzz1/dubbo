package com.cuzz.impl;

import com.cuzz.Log;

public class Log4j implements Log {
    @Override
    public void log(String info) {
        System.out.println("Log4j:" + info);
    }
}
