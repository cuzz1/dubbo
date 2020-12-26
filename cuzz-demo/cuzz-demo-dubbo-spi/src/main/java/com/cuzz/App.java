package com.cuzz;

import com.cuzz.api.Driver;
import org.apache.dubbo.common.extension.ExtensionLoader;
import org.apache.dubbo.rpc.Protocol;
import org.apache.dubbo.rpc.protocol.dubbo.DubboProtocol;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        Driver driver = ExtensionLoader.getExtensionLoader(Driver.class).getExtension("mysqlDriver");
        driver.connect("localhost:3306");
    }
}
