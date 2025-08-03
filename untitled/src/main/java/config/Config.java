package config;

import java.util.List;

public class Config {
    private static String outputPath;
    private static String filePrefix;
    private static boolean appendMode;
    private static boolean shortStatistics;
    private static boolean fullStatistics;
    private static List<String> inputFiles;

    public Config(String outputPath, String filePrefix, boolean appendMode, boolean shortStatistics, boolean fullStatistics, List<String> inputFiles) {
        this.outputPath = outputPath;
        this.filePrefix = filePrefix;
        this.appendMode = appendMode;
        this.shortStatistics = shortStatistics;
        this.fullStatistics = fullStatistics;
        this.inputFiles = inputFiles;
    }

    public static String getOutputPath() {
        return outputPath;
    }

    public static String getFilePrefix() {
        return filePrefix;
    }

    public static boolean isAppendMode() {
        return appendMode;
    }

    public static boolean isShortStatistics() {
        return shortStatistics;
    }

    public static boolean isFullStatistics() {
        return fullStatistics;
    }

    public static List<String> getInputFiles() {
        return inputFiles;
    }
}
