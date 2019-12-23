package com.cosun.cosunp.tool;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author:homey Wong
 * @date:2019/1/23 0023 下午 4:01
 * @Modified By:
 * @Modified-date:
 */
public class IOUtil {
    public static final int THREAD_COUNT = 5;
    private static int filesToBeCompressed = -1;
    private static Lock lock = new ReentrantLock();
    private final static Condition c = lock.newCondition();

    public static int getNumberOfFilesToBeCompressed() {
        return filesToBeCompressed;
    }

    public static void zipFile(List<File> files, ZipOutputStream outputStream) throws IOException, ServletException {
        try {
            int size = files.size();
            // 压缩列表中的文件
            for (int i = 0; i < size; i++) {
                File file = (File) files.get(i);
                zipFile(file, outputStream);
            }
        } catch (IOException e) {
            throw e;
        }
    }

    public static void zipFile(File inputFile, ZipOutputStream outputstream) throws IOException, ServletException {
        try {
            if (inputFile.exists()) {
                if (inputFile.isFile()) {
                    FileInputStream inStream = new FileInputStream(inputFile);
                    BufferedInputStream bInStream = new BufferedInputStream(inStream);
                    ZipEntry entry = new ZipEntry(inputFile.getName());
                    outputstream.putNextEntry(entry);

                    final int MAX_BYTE = 10 * 1024 * 1024;
                    long streamTotal = 0;
                    int streamNum = 0;
                    int leaveByte = 0;
                    byte[] inOutbyte;

                    streamTotal = bInStream.available();
                    streamNum = (int) Math.floor(streamTotal / MAX_BYTE);
                    leaveByte = (int) streamTotal % MAX_BYTE;

                    if (streamNum > 0) {
                        for (int j = 0; j < streamNum; ++j) {
                            inOutbyte = new byte[MAX_BYTE];
                            bInStream.read(inOutbyte, 0, MAX_BYTE);
                            outputstream.write(inOutbyte, 0, MAX_BYTE);
                        }
                    }
                    inOutbyte = new byte[leaveByte];
                    bInStream.read(inOutbyte, 0, leaveByte);
                    outputstream.write(inOutbyte);
                    outputstream.closeEntry();
                    bInStream.close();
                    inStream.close();
                }
            } else {
                throw new ServletException("文件不存在！");
            }
        } catch (IOException e) {
            throw e;
        }
    }

    private static void handleFile(AtomicInteger totalFiles, Queue<File> pool,
                                   File f) {
        totalFiles.addAndGet(1);
        pool.add(f);
        lockedNotify();
    }

    private static void handleDir(AtomicInteger totalFiles, Queue<File> pool,
                                  File f) {
        File[] files = f.listFiles();
        for (int j = 0; j < files.length; j++) {
            if (!files[j].isDirectory()) {
                handleFile(totalFiles, pool, f);
            }
        }
    }

    private static void lockedNotify() {
        try {
            lock.lock();
            c.signalAll();
        } finally {
            lock.unlock();
        }
    }

    public static void downloadFile(File file, HttpServletResponse response, boolean isDelete) throws Exception {
        try {
            BufferedInputStream fis = new BufferedInputStream(new FileInputStream(file.getPath()));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            response.reset();
            OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/octet-stream");
            Cookie cookie = new Cookie("ccc", "111");
            cookie.setPath("/");
            cookie.setMaxAge(3600 * 24);
            response.addCookie(cookie);
            response.setHeader("Content-Disposition", "attachment;filename=" + new String(file.getName().getBytes("UTF-8"), "ISO-8859-1"));
            toClient.write(buffer);
            toClient.flush();
            toClient.close();
            if (isDelete) {
                file.delete();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    public static void downloadFileByCut(File file, boolean isDelete) throws Exception {
        try {
            if (isDelete) {
                file.delete();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

}
