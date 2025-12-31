package com.aeye.mifss.common.utils;

import cn.hutool.core.io.FileUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.http.Method;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.InputStream;
import java.util.Map;

/**
 * HttpClient工具类（基于Hutool HttpUtil）
 *
 * @author mifss
 */
public class HttpClientUtils {

    private static final Logger log = LoggerFactory.getLogger(HttpClientUtils.class);

    private static final int CONNECT_TIMEOUT = 5000;
    private static final int READ_TIMEOUT = 30000;

    /**
     * GET请求
     */
    public static String get(String url) {
        return get(url, null, null);
    }

    /**
     * GET请求（带参数）
     */
    public static String get(String url, Map<String, Object> params) {
        return get(url, params, null);
    }

    /**
     * GET请求（带参数和请求头）
     */
    public static String get(String url, Map<String, Object> params, Map<String, String> headers) {
        try {
            HttpRequest request = HttpRequest.get(url)
                    .timeout(READ_TIMEOUT)
                    .setConnectionTimeout(CONNECT_TIMEOUT);
            if (params != null) {
                request.form(params);
            }
            if (headers != null) {
                request.headerMap(headers, true);
            }
            return request.execute().body();
        } catch (Exception e) {
            log.error("GET请求失败: {}", url, e);
            throw new RuntimeException("GET请求失败", e);
        }
    }

    /**
     * POST表单请求
     */
    public static String postForm(String url, Map<String, Object> params) {
        return postForm(url, params, null);
    }

    /**
     * POST表单请求（带请求头）
     */
    public static String postForm(String url, Map<String, Object> params, Map<String, String> headers) {
        try {
            HttpRequest request = HttpRequest.post(url)
                    .timeout(READ_TIMEOUT)
                    .setConnectionTimeout(CONNECT_TIMEOUT);
            if (params != null) {
                request.form(params);
            }
            if (headers != null) {
                request.headerMap(headers, true);
            }
            return request.execute().body();
        } catch (Exception e) {
            log.error("POST表单请求失败: {}", url, e);
            throw new RuntimeException("POST表单请求失败", e);
        }
    }

    /**
     * POST JSON请求
     */
    public static String postJson(String url, String json) {
        return postJson(url, json, null);
    }

    /**
     * POST JSON请求（带请求头）
     */
    public static String postJson(String url, String json, Map<String, String> headers) {
        try {
            HttpRequest request = HttpRequest.post(url)
                    .timeout(READ_TIMEOUT)
                    .setConnectionTimeout(CONNECT_TIMEOUT)
                    .contentType("application/json;charset=UTF-8");
            if (StringUtils.isNotEmpty(json)) {
                request.body(json);
            }
            if (headers != null) {
                request.headerMap(headers, true);
            }
            return request.execute().body();
        } catch (Exception e) {
            log.error("POST JSON请求失败: {}", url, e);
            throw new RuntimeException("POST JSON请求失败", e);
        }
    }

    /**
     * PUT JSON请求
     */
    public static String putJson(String url, String json) {
        return putJson(url, json, null);
    }

    /**
     * PUT JSON请求（带请求头）
     */
    public static String putJson(String url, String json, Map<String, String> headers) {
        try {
            HttpRequest request = HttpRequest.put(url)
                    .timeout(READ_TIMEOUT)
                    .setConnectionTimeout(CONNECT_TIMEOUT)
                    .contentType("application/json;charset=UTF-8");
            if (StringUtils.isNotEmpty(json)) {
                request.body(json);
            }
            if (headers != null) {
                request.headerMap(headers, true);
            }
            return request.execute().body();
        } catch (Exception e) {
            log.error("PUT请求失败: {}", url, e);
            throw new RuntimeException("PUT请求失败", e);
        }
    }

    /**
     * DELETE请求
     */
    public static String delete(String url) {
        return delete(url, null);
    }

    /**
     * DELETE请求（带请求头）
     */
    public static String delete(String url, Map<String, String> headers) {
        try {
            HttpRequest request = HttpRequest.delete(url)
                    .timeout(READ_TIMEOUT)
                    .setConnectionTimeout(CONNECT_TIMEOUT);
            if (headers != null) {
                request.headerMap(headers, true);
            }
            return request.execute().body();
        } catch (Exception e) {
            log.error("DELETE请求失败: {}", url, e);
            throw new RuntimeException("DELETE请求失败", e);
        }
    }

    /**
     * 上传文件
     */
    public static String uploadFile(String url, String fileParamName, File file) {
        return uploadFile(url, fileParamName, file, null, null);
    }

    /**
     * 上传文件（带额外参数）
     */
    public static String uploadFile(String url, String fileParamName, File file,
            Map<String, Object> params, Map<String, String> headers) {
        try {
            HttpRequest request = HttpRequest.post(url)
                    .timeout(READ_TIMEOUT)
                    .setConnectionTimeout(CONNECT_TIMEOUT)
                    .form(fileParamName, file);
            if (params != null) {
                request.form(params);
            }
            if (headers != null) {
                request.headerMap(headers, true);
            }
            return request.execute().body();
        } catch (Exception e) {
            log.error("上传文件失败: {}", url, e);
            throw new RuntimeException("上传文件失败", e);
        }
    }

    /**
     * 下载文件到指定路径
     */
    public static void downloadFile(String url, String destPath) {
        try {
            HttpUtil.downloadFile(url, FileUtil.file(destPath), READ_TIMEOUT);
        } catch (Exception e) {
            log.error("下载文件失败: {}", url, e);
            throw new RuntimeException("下载文件失败", e);
        }
    }

    /**
     * 下载文件（返回字节数组）
     */
    public static byte[] downloadBytes(String url) {
        try {
            return HttpUtil.downloadBytes(url);
        } catch (Exception e) {
            log.error("下载文件失败: {}", url, e);
            throw new RuntimeException("下载文件失败", e);
        }
    }

    /**
     * 下载文件（返回输入流）
     */
    public static InputStream downloadStream(String url) {
        try {
            HttpResponse response = HttpRequest.get(url)
                    .timeout(READ_TIMEOUT)
                    .execute();
            return response.bodyStream();
        } catch (Exception e) {
            log.error("下载文件失败: {}", url, e);
            throw new RuntimeException("下载文件失败", e);
        }
    }

    /**
     * 通用请求方法
     */
    public static String request(String url, Method method, String body, Map<String, String> headers) {
        try {
            HttpRequest request = HttpUtil.createRequest(method, url)
                    .timeout(READ_TIMEOUT)
                    .setConnectionTimeout(CONNECT_TIMEOUT);
            if (StringUtils.isNotEmpty(body)) {
                request.body(body);
            }
            if (headers != null) {
                request.headerMap(headers, true);
            }
            return request.execute().body();
        } catch (Exception e) {
            log.error("HTTP请求失败: {} {}", method, url, e);
            throw new RuntimeException("HTTP请求失败", e);
        }
    }

    /**
     * 发送请求并获取状态码
     */
    public static int getStatus(String url) {
        try {
            HttpResponse response = HttpRequest.get(url)
                    .timeout(READ_TIMEOUT)
                    .execute();
            return response.getStatus();
        } catch (Exception e) {
            log.error("获取状态码失败: {}", url, e);
            return -1;
        }
    }

    /**
     * 检查URL是否可访问
     */
    public static boolean isReachable(String url) {
        try {
            int status = getStatus(url);
            return status >= 200 && status < 400;
        } catch (Exception e) {
            return false;
        }
    }
}
