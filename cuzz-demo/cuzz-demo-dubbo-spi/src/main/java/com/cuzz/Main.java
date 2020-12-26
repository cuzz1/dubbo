package com.cuzz;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.ExtensionLoader;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.Protocol;


public class Main {
    public static void main(String[] args) {
        Protocol protocol = ExtensionLoader.getExtensionLoader(Protocol.class).getAdaptiveExtension();
        URL url = new URL("dubbo", "localhost", 8888);
        Invoker<String> refer = protocol.refer(String.class, url);
        System.out.println(refer);



    }
}
