package com.aeye.mifss.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.cert.X509Certificate;
import java.util.Map;

/**
 * HTTP工具类
 *
 * @author mifss
 */
public class HttpUtils {

    private static final Logger log = LoggerFactory.getLogger(HttpUtils.class);
    private static final int DEFAULT_CONNECT_TIMEOUT = 5000;
    private static final int DEFAULT_READ_TIMEOUT = 10000;

    public static String get(String url) {
        return get(url, null, null);
    }

    public static String get(String url, Map<String, String> params, Map<String, String> headers) {
        HttpURLConnection conn = null;
        try {
            String fullUrl = buildUrlWithParams(url, params);
            conn = createConnection(fullUrl, "GET", headers);
            return readResponse(conn);
        } catch (Exception e) {
            log.error("GET请求失败，url: {}", url, e);
            throw new RuntimeException("GET请求失败", e);
        } finally {
            if (conn != null)
                conn.disconnect();
        }
    }

    public static String postJson(String url, String json) {
        return postJson(url, json, null);
    }

    public static String postJson(String url, String json, Map<String, String> headers) {
        HttpURLConnection conn = null;
        try {
            conn = createConnection(url, "POST", headers);
            conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            conn.setDoOutput(true);
            if (StringUtils.isNotEmpty(json)) {
                try (OutputStream os = conn.getOutputStream()) {
                    os.write(json.getBytes(StandardCharsets.UTF_8));
                }
            }
            return readResponse(conn);
        } catch (Exception e) {
            log.error("POST请求失败，url: {}", url, e);
            throw new RuntimeException("POST请求失败", e);
        } finally {
            if (conn != null)
                conn.disconnect();
        }
    }

    private static HttpURLConnection createConnection(String urlStr, String method, Map<String, String> headers)
            throws Exception {
        URL url = new URL(urlStr);
        HttpURLConnection conn;
        if (urlStr.startsWith("https")) {
            HttpsURLConnection httpsConn = (HttpsURLConnection) url.openConnection();
            httpsConn.setSSLSocketFactory(createSSLSocketFactory());
            httpsConn.setHostnameVerifier((hostname, session) -> true);
            conn = httpsConn;
        } else {
            conn = (HttpURLConnection) url.openConnection();
        }
        conn.setRequestMethod(method);
        conn.setConnectTimeout(DEFAULT_CONNECT_TIMEOUT);
        conn.setReadTimeout(DEFAULT_READ_TIMEOUT);
        if (headers != null) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                conn.setRequestProperty(entry.getKey(), entry.getValue());
            }
        }
        return conn;
    }

    private static String readResponse(HttpURLConnection conn) throws IOException {
        int code = conn.getResponseCode();
        InputStream is = code >= 400 ? conn.getErrorStream() : conn.getInputStream();
        if (is == null)
            return "";
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
            StringBuilder result = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null)
                result.append(line);
            return result.toString();
        }
    }

    private static String buildUrlWithParams(String url, Map<String, String> params) {
        if (params == null || params.isEmpty())
            return url;
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (sb.length() > 0)
                sb.append("&");
            try {
                sb.append(URLEncoder.encode(entry.getKey(), "UTF-8")).append("=")
                        .append(URLEncoder.encode(entry.getValue() != null ? entry.getValue() : "", "UTF-8"));
            } catch (UnsupportedEncodingException ignored) {
            }
        }
        return url.contains("?") ? url + "&" + sb : url + "?" + sb;
    }

    private static SSLSocketFactory createSSLSocketFactory() throws Exception {
        TrustManager[] tm = new TrustManager[] { new X509TrustManager() {
            public void checkClientTrusted(X509Certificate[] chain, String authType) {
            }

            public void checkServerTrusted(X509Certificate[] chain, String authType) {
            }

            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
        } };
        SSLContext ctx = SSLContext.getInstance("TLS");
        ctx.init(null, tm, new java.security.SecureRandom());
        return ctx.getSocketFactory();
    }
}
