package com.cosun.cosunp.tool;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.net.www.protocol.ftp.FtpURLConnection;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;

/**
 * @author:homey Wong
 * @date:2019/6/11 0011 上午 9:47
 * @Description:
 * @Modified By:
 * @Modified-date:
 */
public class DownLoadUtil {

    private static Logger logger = LoggerFactory.getLogger(DownLoadUtil.class);

    private static final int DOWNLOAD_THREAD_NUM = 15;

    private static ExecutorService downloadExecutorService = ThreadUtil.buildDownloadBatchThreadPool(DOWNLOAD_THREAD_NUM);


    public static void download(String fileUrl, String path) {
        if (!createFolderIfNotExists(path)) {
            return;
        }
        InputStream in = null;
        FileOutputStream out = null;
        try {
            URL url = new URL(fileUrl);
            FtpURLConnection conn = (FtpURLConnection) url.openConnection();
            conn.setConnectTimeout(10000);
            in = conn.getInputStream();
            out = new FileOutputStream(path);
            int len;
            byte[] arr = new byte[1024 * 1000];
            while (-1 != (len = in.read(arr))) {
                out.write(arr, 0, len);
            }
            out.flush();
        } catch (Exception e) {
        } finally {
            try {
                if (null != out) {
                    out.close();
                }
                if (null != in) {
                    in.close();
                }
            } catch (Exception e) {
            }
        }
    }

    private static boolean createFolderIfNotExists(String path) {
        String folderName = getFolder(path);
        if (folderName.equals(path)) {
            return true;
        }
        File folder = new File(getFolder(path));
        if (!folder.exists()) {
            synchronized (DownLoadUtil.class) {
                if (!folder.exists()) {
                    return folder.mkdirs();
                }
            }
        }
        return true;
    }

    private static String getFolder(String path) {
        int index = path.lastIndexOf("/");
        return -1 != index ? path.substring(0, index) : path;
    }

    public static void batch(Map<String, String> resourceMap) {
        if (resourceMap == null || resourceMap.isEmpty()) {
            return;
        }
        try {
            List<String> keys = new ArrayList<>(resourceMap.keySet());
            int size = keys.size();
            int pageNum = getPageNum(size);
            for (int index = 0; index < pageNum; index++) {
                int start = index * DOWNLOAD_THREAD_NUM;
                int last = getLastNum(size, start + DOWNLOAD_THREAD_NUM);
                final CountDownLatch latch = new CountDownLatch(last - start);
                List<String> urlList = keys.subList(start, last);
                for (String url : urlList) {
                    Runnable task = new DownloadWorker(latch, url, resourceMap.get(url));
                    downloadExecutorService.submit(task);
                }
                latch.await();
            }
        } catch (Exception e) {
            logger.error("{}", e);
        }
    }


    private static int getLastNum(int size, int index) {
        return index > size ? size : index;
    }


    private static int getPageNum(int size) {
        int tmp = size / DOWNLOAD_THREAD_NUM;
        return size % DOWNLOAD_THREAD_NUM == 0 ? tmp : tmp + 1;
    }

    static class DownloadWorker implements Runnable {
        private CountDownLatch latch;
        private String url;
        private String path;

        DownloadWorker(CountDownLatch latch, String url, String path) {
            this.latch = latch;
            this.url = url;
            this.path = path;
        }

        @Override
        public void run() {
            logger.debug("Start batch:[{}] into: [{}]", url, path);
            DownLoadUtil.download(url, path);
            logger.debug("Download:[{}] into: [{}] is done", url, path);
            latch.countDown();
        }
    }


}
