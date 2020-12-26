package com.cuzz.rpc.registery;

import org.apache.curator.x.discovery.ServiceInstance;

import java.util.List;


public interface Registry<T> {

    // 注册
    void registerService(ServiceInstance<T> service) throws Exception;

    // 取消注册
    void unregisterService(ServiceInstance<T> service) throws Exception;

    // 查询服务
    List<ServiceInstance<T>> queryForInstances(String name) throws Exception;
}