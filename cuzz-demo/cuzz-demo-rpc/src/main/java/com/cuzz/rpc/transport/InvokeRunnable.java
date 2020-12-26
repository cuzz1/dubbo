package com.cuzz.rpc.transport;

import java.lang.reflect.Method;

import com.cuzz.rpc.factory.BeanManager;
import com.cuzz.rpc.protocol.Header;
import com.cuzz.rpc.protocol.Message;
import com.cuzz.rpc.protocol.Request;
import com.cuzz.rpc.protocol.Response;
import io.netty.channel.ChannelHandlerContext;

class InvokeRunnable implements Runnable {

    private ChannelHandlerContext ctx;
    private Message<Request> message;

    public InvokeRunnable(Message<Request> message, ChannelHandlerContext ctx) {
        this.message = message;
        this.ctx = ctx;
    }

    @Override
    public void run() {
        Response response = new Response();
        Object result = null;
        try {
            Request request = message.getContent();
            String serviceName = request.getServiceName();
            // 这里提供BeanManager对所有业务Bean进行管理，其底层在内存中维护了
            // 一个业务Bean实例的集合。感兴趣的同学可以尝试接入Spring等容器管
            // 理业务Bean
            // TODO
            Object bean = BeanManager.getBean(serviceName);
            System.out.println("serviceName:" + serviceName);
            System.out.println("serviceBean:" + bean);
            // 下面通过反射调用Bean中的相应方法
            Method method = bean.getClass().getMethod(request.getMethodName(), request.getArgTypes());
            result = method.invoke(bean, request.getArgs());
        } catch (Exception e) {
            e.printStackTrace();
            // 省略异常处理
        } finally {
        }
        Header header = message.getHeader();
        header.setExtraInfo((byte) 1);
        response.setResult(result); // 设置响应结果
        // 将响应消息返回给客户端
        ctx.writeAndFlush(new Message(header, response));
    }

}
