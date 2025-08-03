package config;

import java.util.ArrayList;
import java.util.List;

public class ConfigParser implements IArgumentParser<Config> {
    private final String[] args;

    public ConfigParser(String[] args) {
        this.args = args;
    }
    @Override
    public Config parse() {
        String outputPath = "";
        String filePrefix = "";
        boolean appendMode = false;
        boolean shortStatistics = false;
        boolean fullStatistics = false;
        List<String> inputFiles = new ArrayList<>();

        for (int i = 0; i < args.length; i++) {
            String arg = args[i];
            switch (arg) {
                case "-o":
                    if (i + 1 < args.length) {
                        outputPath = args[++i];
                    } else {
                        throw new IllegalArgumentException("Не указан путь для -o");
                    }
                    break;
                case "-p":
                    if (i + 1 < args.length) {
                        filePrefix = args[++i];
                    } else {
                        throw new IllegalArgumentException("Не указан префикс для -p");
                    }
                    break;
                case "-a":
                    appendMode = true;
                    break;
                case "-s":
                    shortStatistics = true;
                    break;
                case "-f":
                    fullStatistics = true;
                    break;
                default:
                    if (!arg.startsWith("-")) {
                        inputFiles.add(arg);
                    } else {
                        throw new IllegalArgumentException("Неизвестная опция: " + arg);
                    }
            }
        }

        if (shortStatistics && fullStatistics) {
            throw new IllegalArgumentException("Можно выбрать только один тип статистики (-s или -f)");
        }

        if (inputFiles.isEmpty()) {
            throw new IllegalArgumentException("Не указаны входные файлы");
        }

        return new Config(outputPath, filePrefix, appendMode, shortStatistics, fullStatistics, inputFiles);
    }
}