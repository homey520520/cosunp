package com.cosun.cosunp.tool;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author:homey Wong
 * @date:2019/6/19 0019 下午 2:56
 * @Description:
 * @Modified By:
 * @Modified-date:
 */
public class CompressDownloadUtil {

    private CompressDownloadUtil() {
    }


    public static HttpServletResponse setDownloadResponse(HttpServletResponse response, String downloadName) {
        response.reset();
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment;fileName*=UTF-8''" + downloadName);
        return response;
    }


    public static Integer[] toIntegerArray(String param) {
        return Arrays.stream(param.split(","))
                .map(Integer::valueOf)
                .toArray(Integer[]::new);
    }


    public static void compressZip(List<File> files, OutputStream outputStream) throws Exception {
        ZipOutputStream zipOutStream = null;
        try {
            zipOutStream = new ZipOutputStream(new BufferedOutputStream(outputStream));
            zipOutStream.setMethod(ZipOutputStream.DEFLATED);
            for (int i = 0; i < files.size(); i++) {
                File file = files.get(i);
                FileInputStream filenputStream = new FileInputStream(file);
                byte[] data = new byte[(int) file.length()];
                filenputStream.read(data);
                zipOutStream.putNextEntry(new ZipEntry(i + file.getName()));
                zipOutStream.write(data);
                filenputStream.close();
                zipOutStream.closeEntry();
            }
        } catch (IOException e) {
            throw e;
        } finally {
            try {
                if (Objects.nonNull(zipOutStream)) {
                    zipOutStream.flush();
                    zipOutStream.close();
                }
                if (Objects.nonNull(outputStream)) {
                    outputStream.close();
                }
            } catch (IOException e) {
                throw e;
            }
        }
    }


    public static void downloadFile(OutputStream outputStream, String zipFilePath) throws Exception{
        File zipFile = new File(zipFilePath);
        if (!zipFile.exists()) {
            return;
        }
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(zipFile);
            byte[] data = new byte[(int) zipFile.length()];
            inputStream.read(data);
            outputStream.write(data);
            outputStream.flush();
        } catch (IOException e) {
            throw e;
        } finally {
            try {
                if (Objects.nonNull(inputStream)) {
                    inputStream.close();
                }
                if (Objects.nonNull(outputStream)) {
                    outputStream.close();
                }
            } catch (IOException e) {
                throw e;
            }
        }
    }


    public static void deleteFile(String filepath) {
        File file = new File(filepath);
        deleteFile(file);
    }

    public static void deleteFile(File file) {
        if (file.isFile() && file.exists()) {
            file.delete();
        }
    }

}

