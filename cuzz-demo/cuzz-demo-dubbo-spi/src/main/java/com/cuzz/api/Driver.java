package com.cuzz.api;

import org.apache.dubbo.common.extension.SPI;

@SPI
public interface Driver {
    void connect(String url);
}
