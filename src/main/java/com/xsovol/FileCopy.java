package com.xsovol;

import java.io.*;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class FileCopy {
    public static void main(String[] args) throws IOException {

        // 读取配置文件
        Properties config = readConfig();
        String projectRootPath = config.getProperty("projectRootPath");
        String targetExportPath = config.getProperty("targetExportPath");

        try {
            // 读取文件清单
            List<String> fileNames = readLines();

            // 复制文件到目标文件夹
            copyFiles(fileNames, projectRootPath, targetExportPath);

            System.out.println("文件复制完成！");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static List<String> readLines() throws IOException {
        List<String> lines = new ArrayList<>();

        // 从resources目录中读取change.txt文件
        try (InputStream input = FileCopy.class.getClassLoader().getResourceAsStream("changes.txt");
             BufferedReader reader = new BufferedReader(new InputStreamReader(input))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        }

        return lines;
    }

    private static void copyFiles(List<String> fileNames, String projectRootPath, String destinationFolder) throws IOException {
        for (String fileName : fileNames) {
            // 构建源文件相对路径
            Path sourceFilePath = FileSystems.getDefault().getPath(fileName);
            // 构建源文件绝对路径
            Path sourceAbsolutePath = FileSystems.getDefault().getPath( projectRootPath+ fileName);

            // 构建目标文件路径
            Path destinationFilePath = FileSystems.getDefault().getPath(destinationFolder, sourceFilePath.toString());

            // 创建目标文件的父目录（如果不存在）
            Path parentFolder = destinationFilePath.getParent();
            if (parentFolder != null && !Files.exists(parentFolder)) {
                Files.createDirectories(parentFolder);
            }

            // 复制文件
            Files.copy(sourceAbsolutePath, destinationFilePath, StandardCopyOption.REPLACE_EXISTING);
        }
    }

    private static Properties readConfig() throws IOException {
        Properties config = new Properties();
        try (InputStream input = FileCopy.class.getClassLoader().getResourceAsStream("config.yaml")) {
            if (input == null) {
                throw new FileNotFoundException("Unable to find config.properties");
            }
            config.load(input);
        }
        return config;
    }
}
