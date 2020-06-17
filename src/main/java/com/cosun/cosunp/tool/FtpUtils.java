package com.cosun.cosunp.tool;

import com.cosun.cosunp.listener.TransforEventListener;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.commons.net.io.Util;

import javax.swing.event.EventListenerList;
import java.io.*;
import java.net.MalformedURLException;

/**
 * @author:homey Wong
 * @date:2019/2/18 0018 上午 10:05
 * @Description:
 * @Modified By:
 * @Modified-date:
 */
public class FtpUtils {

    private EventListenerList eventListenerList = new EventListenerList();

    public static String hostname = "192.168.1.31";
    public static Integer port = 21;
    public static String username = "root";
    public static String password = "123";
    public static FTPClient ftpClient = null;


    public static void initFtpClient() throws Exception {
        ftpClient = new FTPClient();
        ftpClient.setControlEncoding("GBK");
        FTPClientConfig conf = new FTPClientConfig(FTPClientConfig.SYST_NT);
        conf.setServerLanguageCode("zh");
        try {
            ftpClient.connect(hostname, port);
            ftpClient.login(username, password);
            int replyCode = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(replyCode)) {
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
            throw e;
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

    }


    public static boolean uploadFile(String pathname, String fileName, InputStream inputStream) throws Exception {
        boolean flag = false;
        try {
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        try {
            initFtpClient();
            ftpClient.setFileType(ftpClient.BINARY_FILE_TYPE);
            ftpClient.setBufferSize(Util.DEFAULT_COPY_BUFFER_SIZE);
            CreateDirecroty(pathname);
            ftpClient.makeDirectory(pathname);
            ftpClient.changeWorkingDirectory(pathname);
            ftpClient.storeFile(fileName, inputStream);
            inputStream.close();
            ftpClient.logout();
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (ftpClient.isConnected()) {
                try {
                    ftpClient.disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                    throw e;
                }
            }
            if (null != inputStream) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    throw e;
                }
            }
        }
        return true;
    }


    public static boolean changeWorkingDirectory(String directory) throws Exception {
        boolean flag = true;
        try {
            flag = ftpClient.changeWorkingDirectory(directory);
            if (flag) {

            } else {
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
            throw ioe;
        }
        return flag;
    }

    public static boolean CreateDirecroty(String remote) throws Exception {
        boolean success = true;
        String directory = remote + "/";
        if (!directory.equalsIgnoreCase("/") && !changeWorkingDirectory(new String(directory))) {
            int start = 0;
            int end = 0;
            if (directory.startsWith("/")) {
                start = 1;
            } else {
                start = 0;
            }
            end = directory.indexOf("/", start);
            String path = "";
            String paths = "";
            while (true) {
                String subDirectory = remote.substring(start, end);
                path = path + "/" + subDirectory;
                if (!existFile(path)) {
                    if (makeDirectory(subDirectory)) {
                        changeWorkingDirectory(subDirectory);
                    } else {
                        changeWorkingDirectory(subDirectory);
                    }
                } else {
                    changeWorkingDirectory(subDirectory);
                }

                paths = paths + "/" + subDirectory;
                start = end + 1;
                end = directory.indexOf("/", start);
                if (end <= start) {
                    break;
                }
            }
        }
        return success;
    }


    public static boolean existFile(String path) throws IOException {
        boolean flag = false;
        FTPFile[] ftpFileArr = ftpClient.listFiles(path);
        if (ftpFileArr.length > 0) {
            flag = true;
        }
        return flag;
    }


    public static boolean makeDirectory(String dir) throws Exception {
        boolean flag = true;
        try {
            flag = ftpClient.makeDirectory(dir);
            if (flag) {

            } else {
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return flag;
    }


    public static boolean downloadFile(String pathname, String filename, String localpath) throws Exception {
        boolean flag = false;
        OutputStream os = null;
        try {
            initFtpClient();
            ftpClient.changeWorkingDirectory(pathname);
            FTPFile[] ftpFiles = ftpClient.listFiles();
            for (FTPFile file : ftpFiles) {
                if (filename.equalsIgnoreCase(file.getName())) {
                    File localFile = new File(localpath + "/" + file.getName());
                    os = new FileOutputStream(localFile);
                    ftpClient.retrieveFile(file.getName(), os);
                    os.close();
                }
            }
            ftpClient.logout();
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (ftpClient.isConnected()) {
                try {
                    ftpClient.disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                    throw e;
                }
            }
            if (null != os) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    throw e;
                }
            }
        }
        return flag;
    }


    public static boolean deleteFile(String pathname, String filename) throws Exception {
        boolean flag = false;
        try {
            initFtpClient();
            ftpClient.changeWorkingDirectory(pathname);
            ftpClient.dele(filename);
            ftpClient.logout();
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (ftpClient.isConnected()) {
                try {
                    ftpClient.disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                    throw e;
                }
            }
        }
        return flag;
    }


    public static boolean uploadFile(String pathname, String fileName, String originfilename) throws Exception {
        boolean flag = false;
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(new File(originfilename));
            initFtpClient();
            ftpClient.setFileType(ftpClient.BINARY_FILE_TYPE);
            CreateDirecroty(pathname);
            ftpClient.makeDirectory(pathname);
            ftpClient.changeWorkingDirectory(pathname);
            ftpClient.storeFile(fileName, inputStream);
            inputStream.close();
            ftpClient.logout();
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (ftpClient.isConnected()) {
                try {
                    ftpClient.disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                    throw e;
                }
            }
            if (null != inputStream) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    throw e;
                }
            }
        }
        return true;
    }

    public void sendFile(String pathname, String fileName, String originfilename) throws Exception {
        initFtpClient();
        ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
        File _file = new File(originfilename);
        int n = -1;
        long size = _file.length();
        long trans = 0;
        int bufferSize = ftpClient.getBufferSize();
        byte[] buffer = new byte[bufferSize];
        CreateDirecroty(pathname);
        ftpClient.makeDirectory(pathname);
        ftpClient.changeWorkingDirectory(pathname);
        FileInputStream fileInputStream = new FileInputStream(_file);
        String outputpath = pathname + "/" + fileName;
        OutputStream outputstream = ftpClient.storeFileStream(originfilename);
        while ((n = fileInputStream.read(buffer)) != -1) {
            outputstream.write(buffer);
            trans += n;
            TransforEventListener[] listeners = eventListenerList.getListeners(TransforEventListener.class);
            for (int i = listeners.length - 1; i >= 0; i--) {
                listeners[i].update(trans, size);
            }
        }
        fileInputStream.close();
        outputstream.flush();
        outputstream.close();
    }

    public void addListener(TransforEventListener l) {
        eventListenerList.add(TransforEventListener.class, l);
    }

    public void removeListener(TransforEventListener l) {
        eventListenerList.remove(TransforEventListener.class, l);
    }


}
