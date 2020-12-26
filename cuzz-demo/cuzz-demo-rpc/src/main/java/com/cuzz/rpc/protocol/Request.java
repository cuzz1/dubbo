package com.cuzz.rpc.protocol;


import java.io.Serializable;
import java.util.Arrays;

public class Request implements Serializable {
    private String serviceName; // 请求的Service类名
    private String methodName; // 请求的方法名称
    private Class[] argTypes; // 请求方法的参数类型
    private Object[] args; // 请求方法的参数

    public Request(String serviceName, String methodName, Object[] args) {
        this.serviceName = serviceName;
        this.methodName = methodName;
        this.args = args;
        this.argTypes = new Class[args.length];
        for (int i = 0; i < args.length; i++) {
            argTypes[i] = args[i].getClass();
        }
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Class[] getArgTypes() {
        return argTypes;
    }

    public void setArgTypes(Class[] argTypes) {
        this.argTypes = argTypes;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }

    @Override
    public String toString() {
        return "Request{" +
                "serviceName='" + serviceName + '\'' +
                ", methodName='" + methodName + '\'' +
                ", argTypes=" + Arrays.toString(argTypes) +
                ", args=" + Arrays.toString(args) +
                '}';
    }
}