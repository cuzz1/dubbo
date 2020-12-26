package com.cuzz.rpc.test;

import com.cuzz.rpc.proxy.CuzzRpcProxy;
import com.cuzz.rpc.registery.ServerInfo;
import com.cuzz.rpc.registery.ZookeeperRegistry;

public class Consumer {
    public static void main(String[] args) throws Exception {
        // 创建ZookeeperRegistry对象
        ZookeeperRegistry<ServerInfo> discovery = new ZookeeperRegistry<>();
        discovery.start();
        // 创建代理对象，通过代理调用远端Server
        DemoService demoService = CuzzRpcProxy.newInstance(DemoService.class, discovery);
        // 调用sayHello()方法，并输出结果
        Object result =  demoService.sayHello("cuzz");
        System.out.println(result);
        // Thread.sleep(10000000L);
    }
}