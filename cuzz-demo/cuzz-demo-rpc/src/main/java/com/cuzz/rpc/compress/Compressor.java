package com.cuzz.rpc.compress;

import java.io.IOException;

/**
 * 压缩
 */
public interface Compressor {
    byte[] compress(byte[] array) throws IOException;

    byte[] unCompress(byte[] array) throws IOException;
}