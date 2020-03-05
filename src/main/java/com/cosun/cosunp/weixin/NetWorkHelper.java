package com.cosun.cosunp.weixin;

import com.cosun.cosunp.entity.QYWXSPTO;
import com.cosun.cosunp.entity.QYweixinSend;
import com.cosun.cosunp.tool.JSONUtils;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;

import javax.net.ssl.*;
import java.io.*;
import java.net.URL;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * @author:homey Wong
 * @Date: 2019/9/16  上午 11:33
 * @Description:
 * @Modified By:
 * @Modified-date:
 */
public class NetWorkHelper {

    public String getHttpsResponse2(String hsUrl, QYweixinSend text, String requestMethod) {
        URL url;
        InputStream is = null;
        String resultData = "";
        OutputStreamWriter out = null;
        try {
            url = new URL(hsUrl);
            HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
            con.setDoOutput(true);
            con.setDoInput(true);
            TrustManager[] tm = {xtm};

            SSLContext ctx = SSLContext.getInstance("TLS");
            ctx.init(null, tm, null);

            con.setSSLSocketFactory(ctx.getSocketFactory());
            con.setHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String arg0, SSLSession arg1) {
                    return true;
                }
            });


            con.setDoInput(true);

            con.setDoOutput(false);
            con.setUseCaches(false);
            if (null != requestMethod && !requestMethod.equals("")) {
                con.setRequestMethod(requestMethod);
                //con.setRequestProperty("opencheckindatatype", text.getOpencheckindatatype().toString());
                //con.setRequestProperty("starttime", text.getStarttime().toString());
                //con.setRequestProperty("endtime", text.getEndtime().toString());
                // con.setRequestProperty("useridlist", JSONUtils.toJSONString(text.getUseridlist()));
            } else {
                con.setRequestMethod("GET");
            }
            con.setDoOutput(true);
            con.setDoInput(true);
            con.connect();
            out = new OutputStreamWriter(con.getOutputStream(), "GBK");
            out.write(JSONUtils.toJSONString(text).substring(1, JSONUtils.toJSONString(text).length() - 1));
            System.out.println(JSONUtils.toJSONString(text).substring(1, JSONUtils.toJSONString(text).length() - 1));
            out.flush();
            InputStream inStream = con.getInputStream();
            is = con.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader bufferReader = new BufferedReader(isr);
            String inputLine = "";
            while ((inputLine = bufferReader.readLine()) != null) {
                resultData += inputLine + "\n";
            }
            System.out.println(resultData);


            Certificate[] certs = con.getServerCertificates();

            int certNum = 1;

            for (Certificate cert : certs) {
                X509Certificate xcert = (X509Certificate) cert;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultData;
    }



    public String getHttpsResponse3(String hsUrl, QYWXSPTO text, String requestMethod) {
        URL url;
        InputStream is = null;
        String resultData = "";
        OutputStreamWriter out = null;
        try {
            url = new URL(hsUrl);
            HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
            con.setDoOutput(true);
            con.setDoInput(true);
            TrustManager[] tm = {xtm};

            SSLContext ctx = SSLContext.getInstance("TLS");
            ctx.init(null, tm, null);

            con.setSSLSocketFactory(ctx.getSocketFactory());
            con.setHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String arg0, SSLSession arg1) {
                    return true;
                }
            });
            con.setDoInput(true);
            con.setDoOutput(false);
            con.setUseCaches(false);
            if (null != requestMethod && !requestMethod.equals("")) {
                con.setRequestMethod(requestMethod);
            } else {
                con.setRequestMethod("GET");
            }
            con.setDoOutput(true);
            con.setDoInput(true);
            con.connect();
            out = new OutputStreamWriter(con.getOutputStream(), "GBK");
            out.write(JSONUtils.toJSONString(text).substring(1, JSONUtils.toJSONString(text).length() - 1));
            System.out.println(JSONUtils.toJSONString(text).substring(1, JSONUtils.toJSONString(text).length() - 1));
            out.flush();
            InputStream inStream = con.getInputStream();
            is = con.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader bufferReader = new BufferedReader(isr);
            String inputLine = "";
            while ((inputLine = bufferReader.readLine()) != null) {
                resultData += inputLine + "\n";
            }
            System.out.println(resultData);

            Certificate[] certs = con.getServerCertificates();
            int certNum = 1;
            for (Certificate cert : certs) {
                X509Certificate xcert = (X509Certificate) cert;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultData;
    }


    public String getHttpsResponse(String hsUrl, String requestMethod) {
        URL url;
        InputStream is = null;
        String resultData = "";
        try {
            url = new URL(hsUrl);
            HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
            TrustManager[] tm = {xtm};

            SSLContext ctx = SSLContext.getInstance("TLS");
            ctx.init(null, tm, null);

            con.setSSLSocketFactory(ctx.getSocketFactory());
            con.setHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String arg0, SSLSession arg1) {
                    return true;
                }
            });


            con.setDoInput(true);

            con.setDoOutput(false);
            con.setUseCaches(false);
            if (null != requestMethod && !requestMethod.equals("")) {
                con.setRequestMethod(requestMethod);
            } else {
                con.setRequestMethod("GET");
            }
            is = con.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader bufferReader = new BufferedReader(isr);
            String inputLine = "";
            while ((inputLine = bufferReader.readLine()) != null) {
                resultData += inputLine + "\n";
            }
            System.out.println(resultData);


            Certificate[] certs = con.getServerCertificates();

            int certNum = 1;

            for (Certificate cert : certs) {
                X509Certificate xcert = (X509Certificate) cert;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultData;
    }


    public String getHttpsResponse3(String hsUrl, String requestMethod) {
        URL url;
        InputStream bufferReader = null;
        String resultData = "";
        try {
            url = new URL(hsUrl);
            HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
            TrustManager[] tm = {xtm};

            SSLContext ctx = SSLContext.getInstance("TLS");
            ctx.init(null, tm, null);

            con.setSSLSocketFactory(ctx.getSocketFactory());
            con.setHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String arg0, SSLSession arg1) {
                    return true;
                }
            });

            con.setDoInput(true);

            con.setDoOutput(false);
            con.setUseCaches(false);
            if (null != requestMethod && !requestMethod.equals("")) {
                con.setRequestMethod(requestMethod);
            } else {
                con.setRequestMethod("GET");
            }
            bufferReader = con.getInputStream();
            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream("E:\\ftpserver\\weixin\\a.jpg"));



            Certificate[] certs = con.getServerCertificates();

            int certNum = 1;

            for (Certificate cert : certs) {
                X509Certificate xcert = (X509Certificate) cert;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultData;
    }

    X509TrustManager xtm = new X509TrustManager() {
        @Override
        public X509Certificate[] getAcceptedIssuers() {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public void checkServerTrusted(X509Certificate[] arg0, String arg1)
                throws CertificateException {
            // TODO Auto-generated method stub

        }

        @Override
        public void checkClientTrusted(X509Certificate[] arg0, String arg1)
                throws CertificateException {
            // TODO Auto-generated method stub

        }
    };


}
