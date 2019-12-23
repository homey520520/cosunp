package com.cosun.cosunp.tool;

import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.CountDownLatch;

/**
 * @author:homey Wong
 * @date:2019/2/15 0015 上午 10:46
 * @Modified By:
 * @Modified-date:
 */
public class MutiThreadDownLoadUtil {

        private int threadCount;

        private String serverPath;

        private String localPath;

        private CountDownLatch latch;

        public MutiThreadDownLoadUtil(int threadCount, String serverPath, String localPath, CountDownLatch latch) {
            this.threadCount = threadCount;
            this.serverPath = "file:///".concat(serverPath);
            this.localPath = localPath;
            this.latch = latch;
        }

        public void executeDownLoad() {

            try {
                URL url = new URL(serverPath);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(5000);
                conn.setRequestMethod("GET");
                int code = conn.getResponseCode();
                if (code == 200) {
                    int length = conn.getContentLength();
                    RandomAccessFile raf = new RandomAccessFile(localPath, "rwd");
                    raf.setLength(length);
                    raf.close();
                    int blockSize = length / threadCount;
                    for (int threadId = 1; threadId <= threadCount; threadId++) {
                        int startIndex = (threadId - 1) * blockSize;
                        int endIndex = startIndex + blockSize - 1;
                        if (threadId == threadCount) {
                            endIndex = length;
                        }
                        new DownLoadThread(threadId, startIndex, endIndex).start();
                    }

                }

            } catch (Exception e) {
                e.printStackTrace();
            }


        }



        public class DownLoadThread extends Thread {

            private int threadId;

            private int startIndex;

            private int endIndex;

            public DownLoadThread(int threadId, int startIndex, int endIndex) {
                this.threadId = threadId;
                this.startIndex = startIndex;
                this.endIndex = endIndex;
            }


            @Override
            public void run() {

                try {
                    URL url = new URL(serverPath);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    conn.setRequestProperty("Range", "bytes=" + startIndex + "-" + endIndex);
                    conn.setConnectTimeout(5000);
                    int code = conn.getResponseCode();
                    InputStream is = conn.getInputStream();
                    RandomAccessFile raf = new RandomAccessFile(localPath, "rwd");
                    raf.seek(startIndex);

                    int len = 0;
                    byte[] buffer = new byte[1024];
                    while ((len = is.read(buffer)) != -1) {
                        raf.write(buffer, 0, len);
                    }
                    is.close();
                    raf.close();
                    latch.countDown();

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
    }

