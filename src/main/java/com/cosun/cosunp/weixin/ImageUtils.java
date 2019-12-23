package com.cosun.cosunp.weixin;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * @author:homey Wong
 * @Date: 2019/9/23  上午 10:56
 * @Description:
 * @Modified By:
 * @Modified-date:
 */
public class ImageUtils {
    public static boolean saveImage(String accessToken,String temp_path, String serverId, String image_number) {
        boolean isSave = true;

        File saveFile = new File(temp_path);
        if (!saveFile.exists()) {
            saveFile.mkdirs();
        }

        try {
            String s = saveImageToDisk(accessToken,serverId, image_number, temp_path + "/");
            if (s != null || !"".equals(s.trim())) {
                isSave = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isSave;
    }

    private static InputStream getInputStream(String accessToken, String mediaId) {
        InputStream is = null;
        String url = "http://file.api.weixin.qq.com/cgi-bin/media/get?access_token=" + accessToken + "&media_id=" + mediaId;
        try {
            URL urlGet = new URL(url);
            HttpURLConnection http = (HttpURLConnection) urlGet.openConnection();
            http.setRequestMethod("GET");
            http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            http.setDoOutput(true);
            http.setDoInput(true);
            System.setProperty("sun.net.client.defaultConnectTimeout", "30000");
            System.setProperty("sun.net.client.defaultReadTimeout", "30000");
            http.connect();
            is = http.getInputStream();
        } catch (Exception e) {
        }
        return is;

    }

    private static String saveImageToDisk(String accessToken, String serverId, String picName, String picPath) throws Exception {
        InputStream inputStream = getInputStream(accessToken, serverId);
        byte[] data = new byte[1024];
        int len = 0;
        FileOutputStream fileOutputStream = null;

        String filePath = picPath + picName;

        try {
            fileOutputStream = new FileOutputStream(filePath);
            while ((len = inputStream.read(data)) != -1) {
                fileOutputStream.write(data, 0, len);
            }
        } catch (IOException e) {
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                }
            }
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                }
            }
        }


        return filePath;
    }
}
