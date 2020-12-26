package com.cuzz.oracle;

import com.cuzz.api.Driver;

public class OracleDriver implements Driver {
    @Override
    public void connect(String url) {
        System.out.println("connect oracle: " + url);
    }
}
