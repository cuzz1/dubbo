package com.cuzz.rpc.protocol;

public class Message<T> {
    private Header header;
    private T content;

    public Message(Header header, T content) {
        this.header = header;
        this.content= content;
    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    @Override
    public String toString() {
        return "Message{" +
                "header=" + header +
                ", content=" + content +
                '}';
    }
}
