package com.cuzz.impl;

import com.cuzz.Log;

public class Logback implements Log {
    @Override
    public void log(String info) {
        System.out.println("Logback:" + info);
    }
}
