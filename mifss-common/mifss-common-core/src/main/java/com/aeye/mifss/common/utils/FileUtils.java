package com.aeye.mifss.common.utils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.List;

/**
 * 文件工具类
 *
 * @author mifss
 */
public class FileUtils {

    /**
     * 读取文件内容为字符串
     */
    public static String readFileToString(String path) {
        try {
            return new String(Files.readAllBytes(Paths.get(path)), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("读取文件失败: " + path, e);
        }
    }

    /**
     * 读取文件内容为行列表
     */
    public static List<String> readLines(String path) {
        try {
            return Files.readAllLines(Paths.get(path), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("读取文件失败: " + path, e);
        }
    }

    /**
     * 写入字符串到文件
     */
    public static void writeStringToFile(String path, String content) {
        try {
            Files.write(Paths.get(path), content.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new RuntimeException("写入文件失败: " + path, e);
        }
    }

    /**
     * 追加内容到文件
     */
    public static void appendToFile(String path, String content) {
        try {
            Files.write(Paths.get(path), content.getBytes(StandardCharsets.UTF_8),
                    StandardOpenOption.APPEND, StandardOpenOption.CREATE);
        } catch (IOException e) {
            throw new RuntimeException("追加文件失败: " + path, e);
        }
    }

    /**
     * 创建目录（包括父目录）
     */
    public static void mkdirs(String path) {
        try {
            Files.createDirectories(Paths.get(path));
        } catch (IOException e) {
            throw new RuntimeException("创建目录失败: " + path, e);
        }
    }

    /**
     * 删除文件或目录
     */
    public static void delete(String path) {
        try {
            Path p = Paths.get(path);
            if (Files.isDirectory(p)) {
                Files.walk(p).sorted((a, b) -> -a.compareTo(b)).forEach(f -> {
                    try {
                        Files.delete(f);
                    } catch (IOException ignored) {
                    }
                });
            } else {
                Files.deleteIfExists(p);
            }
        } catch (IOException e) {
            throw new RuntimeException("删除失败: " + path, e);
        }
    }

    /**
     * 复制文件
     */
    public static void copy(String src, String dest) {
        try {
            Files.copy(Paths.get(src), Paths.get(dest), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("复制文件失败", e);
        }
    }

    /**
     * 移动/重命名文件
     */
    public static void move(String src, String dest) {
        try {
            Files.move(Paths.get(src), Paths.get(dest), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("移动文件失败", e);
        }
    }

    /**
     * 判断文件或目录是否存在
     */
    public static boolean exists(String path) {
        return Files.exists(Paths.get(path));
    }

    /**
     * 判断是否为目录
     */
    public static boolean isDirectory(String path) {
        return Files.isDirectory(Paths.get(path));
    }

    /**
     * 获取文件扩展名
     */
    public static String getExtension(String filename) {
        if (StringUtils.isEmpty(filename))
            return "";
        int dotIndex = filename.lastIndexOf('.');
        return (dotIndex == -1) ? "" : filename.substring(dotIndex + 1);
    }

    /**
     * 获取文件名（不含扩展名）
     */
    public static String getBaseName(String filename) {
        if (StringUtils.isEmpty(filename))
            return "";
        int dotIndex = filename.lastIndexOf('.');
        return (dotIndex == -1) ? filename : filename.substring(0, dotIndex);
    }

    /**
     * 获取文件大小（字节）
     */
    public static long getSize(String path) {
        try {
            return Files.size(Paths.get(path));
        } catch (IOException e) {
            return -1;
        }
    }

    /**
     * 格式化文件大小
     */
    public static String formatSize(long size) {
        if (size < 1024)
            return size + " B";
        else if (size < 1024 * 1024)
            return String.format("%.2f KB", size / 1024.0);
        else if (size < 1024 * 1024 * 1024)
            return String.format("%.2f MB", size / (1024.0 * 1024));
        else
            return String.format("%.2f GB", size / (1024.0 * 1024 * 1024));
    }
}
