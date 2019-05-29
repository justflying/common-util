package com.self.common.old;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by LiZhong on 2019/1/23.
 */
public class FileEncodingUtil {

    public static String getUploadFileEncoding(InputStream inputStream) {
        String charset = "GBK";
        byte[] first3Bytes = new byte[3];
        try {
            BufferedInputStream bis = new BufferedInputStream(inputStream);
            bis.mark(0);
            int read = bis.read(first3Bytes, 0, 3);
            if (read == -1) {
                charset = "GBK";    //默认
            } else if (first3Bytes[0] == -1 && first3Bytes[1] == -2) {
                charset = "UTF-16LE"; // 文件编码为 Unicode
            } else if (first3Bytes[0] == -2 && first3Bytes[1] == -1) {
                charset = "UTF-16BE"; // 文件编码为 Unicode big endian
            } else if ((first3Bytes[0] == (byte) 0xEF && first3Bytes[1] == (byte) 0xBB && first3Bytes[2] == (byte) 0xBF)
                    || (first3Bytes[0] == -17 && first3Bytes[1] == -69 && first3Bytes[2] == -65)) {
                charset = "UTF-8"; // 文件编码为 UTF-8
            }
            bis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return charset;
    }

    public static Boolean encodingIsUTF8(InputStream inputStream) {
        if ("UTF-8".equals(getUploadFileEncoding(inputStream))) {
            return true;
        }
        return false;
    }

}
