package com.cosun.cosunp.tool;


import com.cosun.cosunp.controller.PersonController;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.MediaType;

import javax.net.ssl.*;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author:homey Wong
 * @Date: 2019/9/26 0026 下午 4:37
 * @Description:
 * @Modified By:
 * @Modified-date:
 */
public class HttpUtil {


    private static Logger log = LogManager.getLogger(HttpUtil.class);

    protected static final String POST_METHOD = "POST";

    private static final String GET_METHOD = "GET";

    static {
        TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
                log.debug("ClientTrusted");
            }

            @Override
            public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
                log.debug("ServerTrusted");
            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[]{};
            }
        }};

        HostnameVerifier doNotVerify = (s, sslSession) -> true;

        try {
            SSLContext sc = SSLContext.getInstance("SSL", "SunJSSE");
            sc.init(null, trustAllCerts, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            HttpsURLConnection.setDefaultHostnameVerifier(doNotVerify);
        } catch (Exception e) {
            log.error("Initialization https impl occur exception : {}", e);
        }
    }


    private static String HttpDefaultExecute(String url, String method, Map<String, String> map, String data) {
        String result = "";
        try {
            url = setParmas(url, map, null);
            result = defaultConnection(url, method, data);
        } catch (Exception e) {
            log.error("出错参数 {}", map);
        }
        return result;
    }


    private static String defaultConnection(String path, String method, String data) throws Exception {
        if (StringUtils.isBlank(path)) {
            throw new IOException("url can not be null");
        }
        String result = null;
        URL url = new URL(path);
        HttpURLConnection conn = getConnection(url, method);
        if (StringUtils.isNotEmpty(data)) {
            OutputStream output = conn.getOutputStream();
            output.write(data.getBytes(StandardCharsets.UTF_8));
            output.flush();
            output.close();
        }
        if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
            InputStream input = conn.getInputStream();
            result = IOUtils.toString(input,"UTF_8");
            input.close();
            conn.disconnect();
        }
        return result;
    }


    public static String setParmas(String url, Map<String, String> map, String charset) throws Exception {
        String result = StringUtils.EMPTY;
        boolean hasParams = false;
        if (StringUtils.isNotEmpty(url) && MapUtils.isNotEmpty(map)) {
            StringBuilder builder = new StringBuilder();
            for (Map.Entry<String, String> entry : map.entrySet()) {
                String key = entry.getKey().trim();
                String value = entry.getValue().trim();
                if (hasParams) {
                    builder.append("&");
                } else {
                    hasParams = true;
                }
                if (StringUtils.isNotEmpty(charset)) {
                    builder.append(key).append("=").append(URLEncoder.encode(value, charset));
                } else {
                    builder.append(key).append("=").append(value);
                }
            }
            result = builder.toString();
        }

        URL u = new URL(url);
        if (StringUtils.isEmpty(u.getQuery())) {
            if (url.endsWith("?")) {
                url += result;
            } else {
                url = url + "?" + result;
            }
        } else {
            if (url.endsWith("&")) {
                url += result;
            } else {
                url = url + "&" + result;
            }
        }
        log.debug("request url is {}", url);
        return url;
    }

    public static String httpGet(String url, Map<String, String> map) {
        return HttpDefaultExecute(url, GET_METHOD, map, null);
    }

    public static String httpPost(String url, Map<String, String> map, String data) {
        return HttpDefaultExecute(url, POST_METHOD, map, data);
    }


    public static String HttpsDefaultExecute(String url, String method, Map<String, String> map, String data) {
        try {
            url = setParmas(url, map, null);
            log.info(data);
            return defaultConnection(url, method, data);
        } catch (Exception e) {
            log.error("出错参数 {}", map);
        }
        return "";
    }

    public static String doGet(String url, Map<String, String> map) {
        return HttpsDefaultExecute(url, GET_METHOD, map, null);
    }

    public static String doPost(String url, Map<String, String> map, String data) {
        return HttpsDefaultExecute(url, POST_METHOD, map, data);
    }




    protected static HttpURLConnection getConnection(URL url, String method) throws IOException {
        HttpURLConnection conn;
        if (StringUtils.equals("https", url.getProtocol())) {
            conn = (HttpsURLConnection) url.openConnection();
        } else {
            conn = (HttpURLConnection) url.openConnection();
        }
        if (conn == null) {
            throw new IOException("connection can not be null");
        }
        conn.setRequestProperty("Pragma", "no-cache");
        conn.setRequestProperty("Cache-Control", "no-cache");
        conn.setRequestProperty("Connection", "Close");
        conn.setUseCaches(false);
        conn.setDoOutput(true);
        conn.setDoInput(true);
        conn.setInstanceFollowRedirects(true);
        conn.setRequestMethod(method);
        conn.setConnectTimeout(8000);
        conn.setReadTimeout(8000);

        return conn;
    }



    protected static HttpURLConnection getConnection(URL url, boolean isFile) throws IOException {
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        if (conn == null) {
            throw new IOException("connection can not be null");
        }
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setUseCaches(false);
        if (isFile) {
            conn.setRequestMethod(POST_METHOD);
        }
        conn.setRequestProperty("Connection", "Keep-Alive");
        conn.setRequestProperty("Charset", String.valueOf(StandardCharsets.UTF_8));
        conn.setConnectTimeout(8000);
        conn.setReadTimeout(8000);
        return conn;
    }




    protected static String getFileName(String mediaUrl) {
        return StringUtils.substringAfterLast(mediaUrl, "/");
    }


    public static String getRemortIP(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        if (ip != null && ip.indexOf(",") > 0 && ip.split(",").length > 1) {
            ip = ip.split(",")[0];
        }
        return ip;
    }


    public static void toOutput(String url, OutputStream output) throws IOException {
        URL path = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) path.openConnection();
        connection.setDoInput(true);
        connection.connect();
        try (InputStream input = connection.getInputStream()) {
            IOUtils.copy(input, output);
            connection.disconnect();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }


}
