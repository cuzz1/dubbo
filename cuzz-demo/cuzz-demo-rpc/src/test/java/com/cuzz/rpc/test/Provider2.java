package com.cuzz.rpc.test;

import com.cuzz.rpc.factory.BeanManager;
import com.cuzz.rpc.registery.ServerInfo;
import com.cuzz.rpc.registery.ZookeeperRegistry;
import com.cuzz.rpc.transport.CuzzRpcServer;
import org.apache.curator.x.discovery.ServiceInstance;

public class Provider2 {
    public static void main(String[] args) throws Exception {
        // 创建DemoServiceImpl，并注册到BeanManager中
        BeanManager.registerBean("demoService",
                new DemoServiceImpl());
        // 创建ZookeeperRegistry，并将Provider的地址信息封装成ServerInfo
        // 对象注册到Zookeeper
        ZookeeperRegistry<ServerInfo> discovery =
                new ZookeeperRegistry<>();
        discovery.start();
        ServerInfo serverInfo = new ServerInfo("127.0.0.1", 20881);
        discovery.registerService(
                ServiceInstance.<ServerInfo>builder().name("demoService")
                        .payload(serverInfo).build());
        // 启动DemoRpcServer，等待Client的请求
        CuzzRpcServer rpcServer = new CuzzRpcServer(20881);
        rpcServer.start();
        Thread.sleep(100000000L);
    }


    public static String getPackName() {
        Package pack = Provider2.class.getPackage();
        String packName = pack.getName();
        return packName;
    }

}