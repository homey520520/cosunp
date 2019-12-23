package com.cosun.cosunp.tool;

import java.io.*;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.zip.GZIPOutputStream;

/**
 * @author:homey Wong
 * @date:2019/2/15 0015 下午 2:26
 * @Modified By:
 * @Modified-date:
 */
public class GZipThreadUtil extends Thread {
    private Queue<File> pool;
    private static AtomicInteger filesCompressed = new AtomicInteger(0);
    private Condition c;

    public GZipThreadUtil(Queue<File> pool, Condition c) {
        this.pool = pool;
        this.c = c;
    }

    private static void incrementFilesCompressed() {
        filesCompressed.addAndGet(1);
    }

    public void run() {
        while (filesCompressed.get() != IOUtil
                .getNumberOfFilesToBeCompressed()) {
            while (pool.isEmpty()) {
                isEndOfThread();
                awaitThread();
            }
            File input = (File) pool.remove();
            incrementFilesCompressed();
            if (!input.getName().equals(".gz")) {
                compress(input);
            }
        }
    }

    private void awaitThread(){
        try {
            c.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean isEndOfThread() {
        if (filesCompressed.get() == IOUtil
                .getNumberOfFilesToBeCompressed()) {
            return true;
        }
        return false;
    }

    private void compress(File input){
        InputStream in = null;
        OutputStream out = null;
        try {
            in = new FileInputStream(input);
            in = new BufferedInputStream(in);
            File output = new File(input.getParent(), input.getName() + ".gz");
            if (!output.exists()) {
                out = new FileOutputStream(output);
                out = new GZIPOutputStream(out);
                out = new BufferedOutputStream(out);
                int b;
                while ((b = in.read()) != -1)
                    out.write(b);
                out.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(out);
            close(in);
        }
    }

    private void close(Closeable c)  {
        if (c != null) {
            try {
                c.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
