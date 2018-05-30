package com.ivmai.kehu.tools;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import java.io.IOException;
import java.io.OutputStream;

public class BASE64Encoder extends CharacterEncoder {
    private static final char[] pem_array = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '/'};

    public BASE64Encoder() {
    }

    protected int bytesPerAtom() {
        return 3;
    }

    protected int bytesPerLine() {
        return 57;
    }

    protected void encodeAtom(OutputStream outputstream, byte[] abyte0, int i, int j) throws IOException {
        byte byte2;
        if(j == 1) {
            byte2 = abyte0[i];
            byte byte4 = 0;
            boolean byte5 = false;
            outputstream.write(pem_array[byte2 >>> 2 & 63]);
            outputstream.write(pem_array[(byte2 << 4 & 48) + (byte4 >>> 4 & 15)]);
            outputstream.write(61);
            outputstream.write(61);
        } else {
            byte byte41;
            if(j == 2) {
                byte2 = abyte0[i];
                byte41 = abyte0[i + 1];
                byte byte51 = 0;
                outputstream.write(pem_array[byte2 >>> 2 & 63]);
                outputstream.write(pem_array[(byte2 << 4 & 48) + (byte41 >>> 4 & 15)]);
                outputstream.write(pem_array[(byte41 << 2 & 60) + (byte51 >>> 6 & 3)]);
                outputstream.write(61);
            } else {
                byte2 = abyte0[i];
                byte41 = abyte0[i + 1];
                byte byte52 = abyte0[i + 2];
                outputstream.write(pem_array[byte2 >>> 2 & 63]);
                outputstream.write(pem_array[(byte2 << 4 & 48) + (byte41 >>> 4 & 15)]);
                outputstream.write(pem_array[(byte41 << 2 & 60) + (byte52 >>> 6 & 3)]);
                outputstream.write(pem_array[byte52 & 63]);
            }
        }

    }
}