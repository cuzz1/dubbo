package com.cuzz.rpc.compress;

public class CompressorFactory {
    public static Compressor get(byte extraInfo) {
        return new SnappyCompressor();
    }
}
