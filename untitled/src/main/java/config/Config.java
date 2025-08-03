package config;

import java.util.List;

public class Config {
    private String outputPath;
    private String filePrefix;
    private boolean appendMode;
    private boolean shortStatistics;
    private boolean fullStatistics;
    private List<String> inputFiles;

    public Config(String outputPath, String filePrefix, boolean appendMode, boolean shortStatistics, boolean fullStatistics, List<String> inputFiles) {
        this.outputPath = outputPath;
        this.filePrefix = filePrefix;
        this.appendMode = appendMode;
        this.shortStatistics = shortStatistics;
        this.fullStatistics = fullStatistics;
        this.inputFiles = inputFiles;
    }

    public String getOutputPath() {
        return outputPath;
    }

    public String getFilePrefix() {
        return filePrefix;
    }

    public boolean isAppendMode() {
        return appendMode;
    }

    public boolean isShortStatistics() {
        return shortStatistics;
    }

    public boolean isFullStatistics() {
        return fullStatistics;
    }

    public List<String> getInputFiles() {
        return inputFiles;
    }
}
