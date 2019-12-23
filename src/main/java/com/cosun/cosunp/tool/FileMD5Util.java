package com.cosun.cosunp.tool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.AccessController;
import java.security.MessageDigest;
import java.security.PrivilegedAction;

/**
 * version 1.0
 */
public class FileMD5Util {

    private final static Logger logger = LoggerFactory.getLogger(FileMD5Util.class);

    public static String getFileMD5(File file) throws Exception {
        String value = null;
        FileInputStream in = new FileInputStream(file);
        MappedByteBuffer byteBuffer = null;
        try {
            byteBuffer = in.getChannel().map(FileChannel.MapMode.READ_ONLY, 0, file.length());
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(byteBuffer);
            BigInteger bi = new BigInteger(1, md5.digest());
            value = bi.toString(16);
            if (value.length() < 32) {
                value = "0" + value;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (null != in) {
                try {
                    in.getChannel().close();
                    in.close();
                } catch (IOException e) {
                    throw e;
                }
            }
            if (null != byteBuffer) {
                freedMappedByteBuffer(byteBuffer);
            }
        }
        return value;
    }


    public static void freedMappedByteBuffer(final MappedByteBuffer mappedByteBuffer) throws Exception{
        try {
            if (mappedByteBuffer == null) {
                return;
            }

            mappedByteBuffer.force();
            AccessController.doPrivileged(new PrivilegedAction<Object>() {
                @Override
                public Object run() {
                    try {
                        Method getCleanerMethod = mappedByteBuffer.getClass().getMethod("cleaner", new Class[0]);
                        getCleanerMethod.setAccessible(true);
                        sun.misc.Cleaner cleaner = (sun.misc.Cleaner) getCleanerMethod.invoke(mappedByteBuffer,
                                new Object[0]);
                        cleaner.clean();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return null;
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}
