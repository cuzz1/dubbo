package com.cuzz.rpc.test;

/**
 * @author cuzz
 * @date 2020/12/26 19:57
 */
public class DemoServiceImpl implements DemoService{
    @Override
    public String sayHello(String params) {
        return "hello: " + params;
    }
}
