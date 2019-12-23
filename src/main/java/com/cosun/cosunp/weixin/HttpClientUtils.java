package com.cosun.cosunp.weixin;

import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Lists;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NoHttpResponseException;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpContext;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.InterruptedIOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.charset.CodingErrorAction;
import java.util.List;
import java.util.Map;


/**
 * @author:homey Wong
 * @Date: 2019/9/17 0017 下午 5:19
 * @Description:
 * @Modified By:
 * @Modified-date:
 */
public class HttpClientUtils {


    private static final int MAX_TOTAL_CONNECTIONS = 4000;

    private static final int DEFAULT_MAX_PER_ROUTE = 200;


    private static final int REQUEST_CONNECTION_TIMEOUT = 8 * 1000;


    private static final int REQUEST_SOCKET_TIMEOUT = 8 * 1000;

    private static final int REQUEST_CONNECTION_REQUEST_TIMEOUT = 5 * 1000;


    private static final int VALIDATE_AFTER_IN_ACTIVITY = 2 * 1000;

    private static final int SOCKET_CONFIG_SO_LINGER = 60;


    private static final int SOCKET_CONFIG_SO_TIMEOUT = 5 * 1000;

    private static int RETRY_COUNT = 5;

    private static volatile CloseableHttpClient httpClient = null;


    public static String doGet(String uri) {

        StringBuilder result = new StringBuilder();
        try {
            String res = "";
            URL url = new URL(uri);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                res += line + "\n";
            }
            in.close();
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }


    public static String doGet(String uri, Map<String, String> params) {

        return doGet(getGetUrlFromParams(uri, params));

    }


    private static String getGetUrlFromParams(String uri, Map<String, String> params) {


        List<BasicNameValuePair> resultList = FluentIterable.from(params.entrySet()).transform(
                new Function<Map.Entry<String, String>, BasicNameValuePair>() {
                    @Override
                    public BasicNameValuePair apply(Map.Entry<String, String> innerEntry) {

                        return new BasicNameValuePair(innerEntry.getKey(), innerEntry.getValue());
                    }

                }).toList();

        String paramSectionOfUrl = URLEncodedUtils.format(resultList, Consts.UTF_8);
        StringBuffer resultUrl = new StringBuffer(uri);

        if (StringUtils.isEmpty(uri)) {
            return uri;
        } else {
            if (!StringUtils.isEmpty(paramSectionOfUrl)) {
                if (uri.endsWith("?")) {
                    resultUrl.append(paramSectionOfUrl);
                } else {
                    resultUrl.append("?").append(paramSectionOfUrl);
                }
            }
            return resultUrl.toString();
        }


    }



    public static String doPost(String uri, Map<String, String> params) {

        String responseBody;
        HttpPost httpPost = new HttpPost(uri);
        try {
            List<NameValuePair> nvps = Lists.newArrayList();
            for (Map.Entry<String, String> entry : params.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                nvps.add(new BasicNameValuePair(key, value));
            }
            httpPost.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));
            httpPost.setConfig(getRequestConfig());
            responseBody = executeRequest(httpPost);

        } catch (Exception e) {
            throw new RuntimeException("httpclient doPost方法异常 ", e);
        } finally {
            httpPost.releaseConnection();
        }

        return responseBody;

    }


    public static String doPost(String uri, String param, ContentType contentType) {

        String responseBody;
        HttpPost httpPost = new HttpPost(uri);
        try {
            StringEntity reqEntity = new StringEntity(param, contentType);
            httpPost.setEntity(reqEntity);
            httpPost.setConfig(getRequestConfig());
            responseBody = executeRequest(httpPost);

        } catch (IOException e) {
            throw new RuntimeException("httpclient doPost方法异常 ", e);
        } finally {
            httpPost.releaseConnection();
        }
        return responseBody;
    }

    private static RequestConfig getRequestConfig() {


        RequestConfig defaultRequestConfig = RequestConfig.custom()
                //.setCookieSpec(CookieSpecs.DEFAULT)
                .setExpectContinueEnabled(true)
                //.setTargetPreferredAuthSchemes(Arrays.asList(AuthSchemes.NTLM, AuthSchemes.DIGEST))
                //.setProxyPreferredAuthSchemes(Arrays.asList(AuthSchemes.BASIC))
                .build();

        return RequestConfig.copy(defaultRequestConfig)
                .setSocketTimeout(REQUEST_CONNECTION_TIMEOUT)
                .setConnectTimeout(REQUEST_SOCKET_TIMEOUT)
                .setConnectionRequestTimeout(REQUEST_CONNECTION_REQUEST_TIMEOUT)
                .build();

    }



    private static String executeRequest(HttpUriRequest method) throws IOException {

        ResponseHandler<String> responseHandler = new ResponseHandler<String>() {

            @Override
            public String handleResponse(final HttpResponse response) throws IOException {

                int status = response.getStatusLine().getStatusCode();
                String result;
                if (status >= HttpStatus.SC_OK && status < HttpStatus.SC_MULTIPLE_CHOICES) {
                    HttpEntity entity = response.getEntity();
                    result = entity != null ? EntityUtils.toString(entity) : null;
                    EntityUtils.consume(entity);
                    return result;
                } else {
                    throw new ClientProtocolException("Unexpected response status: " + status);
                }
            }

        };
        String result = getHttpClientInstance().execute(method, responseHandler);

        return result;
    }



    private static CloseableHttpClient getHttpClientInstance() {

        if (httpClient == null) {
            synchronized (CloseableHttpClient.class) {
                if (httpClient == null) {
                    httpClient = HttpClients.custom().setConnectionManager(initConfig()).setRetryHandler(getRetryHandler()).build();
                }
            }
        }
        return httpClient;

    }


    private static HttpRequestRetryHandler getRetryHandler() {


        return new HttpRequestRetryHandler() {
            @Override
            public boolean retryRequest(IOException exception,
                                        int executionCount, HttpContext context) {
                if (executionCount >= RETRY_COUNT) {
                    return false;
                }
                if (exception instanceof NoHttpResponseException) {
                    return true;
                }
                if (exception instanceof SSLHandshakeException) {
                    return false;
                }
                if (exception instanceof InterruptedIOException) {
                    return false;
                }
                if (exception instanceof UnknownHostException) {
                    return false;
                }
                if (exception instanceof ConnectTimeoutException) {
                    return false;
                }
                if (exception instanceof SSLException) {
                    return false;
                }

                HttpRequest request = HttpClientContext.adapt(context).getRequest();
                return !(request instanceof HttpEntityEnclosingRequest);
            }
        };

    }



    private static PoolingHttpClientConnectionManager initConfig() {

        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.INSTANCE)
                .register("https", new SSLConnectionSocketFactory(SSLContexts.createSystemDefault()))
                .build();

        PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);



        SocketConfig socketConfig = SocketConfig.custom()
                .setTcpNoDelay(true)
                .setSoReuseAddress(true)
                .setSoTimeout(SOCKET_CONFIG_SO_TIMEOUT)
                //.setSoLinger(SOCKET_CONFIG_SO_LINGER)
                //.setSoKeepAlive(true)
                .build();

        connManager.setDefaultSocketConfig(socketConfig);
        connManager.setValidateAfterInactivity(VALIDATE_AFTER_IN_ACTIVITY);

        ConnectionConfig connectionConfig = ConnectionConfig.custom()
                .setMalformedInputAction(CodingErrorAction.IGNORE)
                .setUnmappableInputAction(CodingErrorAction.IGNORE)
                .setCharset(Consts.UTF_8)
                .build();
        connManager.setDefaultConnectionConfig(connectionConfig);
        connManager.setDefaultMaxPerRoute(DEFAULT_MAX_PER_ROUTE);
        connManager.setMaxTotal(MAX_TOTAL_CONNECTIONS);
        return connManager;

    }


}
