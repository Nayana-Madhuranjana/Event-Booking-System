package com.eventbooking.util;

import jakarta.servlet.ServletContext;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;

public class FileUtil {
    private static final String DATA_DIR = "/WEB-INF/data/";

    public static Path dataPath(ServletContext context, String fileName) throws IOException {
        String real = context.getRealPath(DATA_DIR + fileName);
        if (real == null) {
            throw new IOException("Cannot resolve data path for " + fileName + ". Deploy as exploded WAR in Tomcat.");
        }
        Path path = Paths.get(real);
        Files.createDirectories(path.getParent());
        if (!Files.exists(path)) Files.createFile(path);
        return path;
    }

    public static List<String> readLines(ServletContext context, String fileName) {
        try {
            Path path = dataPath(context, fileName);
            return Files.readAllLines(path, StandardCharsets.UTF_8);
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    public static void writeLines(ServletContext context, String fileName, List<String> lines) {
        try {
            Path path = dataPath(context, fileName);
            Files.write(path, lines, StandardCharsets.UTF_8, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.CREATE);
        } catch (IOException e) {
            throw new RuntimeException("Failed to write " + fileName, e);
        }
    }

    public static void appendLine(ServletContext context, String fileName, String line) {
        try {
            Path path = dataPath(context, fileName);
            Files.writeString(path, line + System.lineSeparator(), StandardCharsets.UTF_8, StandardOpenOption.APPEND, StandardOpenOption.CREATE);
        } catch (IOException e) {
            throw new RuntimeException("Failed to append " + fileName, e);
        }
    }

    public static String nextId(String prefix, int currentSize) {
        return prefix + String.format("%03d", currentSize + 1);
    }
}
