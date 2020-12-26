package com.cuzz.mysql;

import com.cuzz.api.Driver;

public class MysqlDriver implements Driver {
    @Override
    public void connect(String url) {
        System.out.println("connect mysql: " + url);
    }
}
