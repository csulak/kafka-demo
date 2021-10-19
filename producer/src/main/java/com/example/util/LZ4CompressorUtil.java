package com.example.util;

import kotlin.text.Charsets;
import net.jpountz.lz4.LZ4Factory;
import net.jpountz.lz4.LZ4FastDecompressor;
import net.jpountz.lz4.LZ4SafeDecompressor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

@Component
public class LZ4CompressorUtil {

    private final LZ4Factory lz4Factory;

    private static final Logger LOGGER = LoggerFactory.getLogger(LZ4CompressorUtil.class);

    public LZ4CompressorUtil() {
        this.lz4Factory = LZ4Factory.safeInstance();
    }

    public byte[] compress(String data) {

        if(data == null || data.isEmpty()){
            LOGGER.error("Trying to compress null or empty data: {}", data);
            return new byte[0];
        }
        byte[] decompressed = data.getBytes(StandardCharsets.UTF_8);
        net.jpountz.lz4.LZ4Compressor compressor = lz4Factory.fastCompressor();
        int maxCompressedLength = compressor.maxCompressedLength(decompressed.length);
        byte[] compressed = new byte[4 + maxCompressedLength];
        int compressedSize = compressor.compress(decompressed, 0, decompressed.length,
                compressed, 4, maxCompressedLength);
        ByteBuffer.wrap(compressed).putInt(decompressed.length);
        //return Arrays.copyOf(compressed, 0, 4 + compressedSize);
        return Arrays.copyOf(compressed,4 + compressedSize);
    }

    public String deCompress(byte[] data) {

        if(data == null || data.length == 0){
            LOGGER.error("Trying to decompress null or empty data array: {}", data);
            return "";
        }
        LZ4FastDecompressor decompressor = lz4Factory.fastDecompressor();
        int decrompressedLength = ByteBuffer.wrap(data).getInt();
        byte[] restored = new byte[decrompressedLength];
        decompressor.decompress(data, 4, restored, 0, decrompressedLength);
        return new String(restored, StandardCharsets.UTF_8);
    }


    /**
     * Method not used but it's a posibility to decompress if you don't know the original length
     */
    public String getDecompressedMessage(byte[] data){

        LZ4SafeDecompressor decompressor = lz4Factory.safeDecompressor();
        int maxUncompressedLength = 255 * data.length;
        byte[] restored  = decompressor.decompress(data,  maxUncompressedLength);
        String restoredMessage = new String(restored, Charsets.UTF_8) ;

        return restoredMessage ;
    }


}