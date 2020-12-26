package com.cuzz.rpc.codec;

import com.cuzz.rpc.Const;
import com.cuzz.rpc.compress.Compressor;
import com.cuzz.rpc.compress.CompressorFactory;
import com.cuzz.rpc.protocol.Header;
import com.cuzz.rpc.protocol.Message;
import com.cuzz.rpc.protocol.Request;
import com.cuzz.rpc.serialization.Serialization;
import com.cuzz.rpc.serialization.SerializationFactory;
import com.cuzz.rpc.util.JsonUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class CuzzRpcDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf byteBuf, List<Object> out) throws Exception {

        // 不到16字节的化无法解析消息头，暂时不读
        if (byteBuf.readableBytes() < Const.HEADER_SIZE) {
            return;
        }
        // 记录当前readIndex指针的位置，方便重置
        byteBuf.markReaderIndex();
        // 尝试读取消息头的魔数部分
        short magic = byteBuf.readShort();
        if (magic != Const.MAGIC) { // 魔数不匹配
            byteBuf.resetReaderIndex(); // 重置readIndex指针
            throw new RuntimeException("magic number error:" + magic);
        }
        // 依次读取消息版本、附加信息、消息ID以及消息体长度四部分
        byte version = byteBuf.readByte();
        byte extraInfo = byteBuf.readByte();
        long messageId = byteBuf.readLong();
        int size = byteBuf.readInt();
        Object request = null;
        // 心跳消息是没有消息体的，无需读取
        if (!Const.isHeartBeat(extraInfo)) {
            // 对于非心跳消息，没有积累到足够的数据是无法进行反序列化的
            if (byteBuf.readableBytes() < size) {
                byteBuf.resetReaderIndex();
                return;
            }
            // 读取消息体并且进行反序列化
            byte[] payload = new byte[size];
            byteBuf.readBytes(payload);
            // 这里根据消息头中的extraInfo部分选择相应的序列化和压缩方式
            Serialization serialization = SerializationFactory.get(extraInfo);
            Compressor compressor = CompressorFactory.get(extraInfo);
            // 得到消息体
            request = serialization.deSerialize(compressor.unCompress(payload), Request.class);
        }
        // 将上面读取到的消息头和消息体拼装成完整的Message并向后传递
        Header header = new Header(magic, version, extraInfo, messageId, size);
        Message message = new Message(header, request);
        System.out.println("server msg:" + JsonUtils.toJson(message));
        out.add(message);
    }

}
