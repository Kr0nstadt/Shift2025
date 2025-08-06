package config;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ConfigParser implements IArgumentParser<Config> {
    private static final Set<String> VALID_FLAGS = Set.of("-o", "-p", "-a", "-s", "-f", "--");
    private final String[] args;

    public ConfigParser(String[] args) {
        this.args = args;
    }

    @Override
    public Config parse() throws IllegalArgumentException {
        String outputPath = "";
        String filePrefix = "";
        boolean appendMode = false;
        boolean shortStatistics = false;
        boolean fullStatistics = false;
        List<String> inputFiles = new ArrayList<>();
        boolean treatAllAsFiles = false;

        for (int i = 0; i < args.length; i++) {
            String arg = args[i];

            if (treatAllAsFiles) {
                inputFiles.add(arg);
                continue;
            }

            if (arg.startsWith("-")) {
                if (arg.equals("--")) {
                    treatAllAsFiles = true;
                    continue;
                }

                if (!VALID_FLAGS.contains(arg)) {
                    if (!isLikelyFilename(arg)) {
                        throw new IllegalArgumentException("Unknown option: " + arg);
                    }
                    inputFiles.add(arg);
                    continue;
                }

                switch (arg) {
                    case "-o":
                        outputPath = getRequiredValue(args, i++, "No path specified for -o");
                        break;
                    case "-p":
                        filePrefix = getRequiredValue(args, i++, "No prefix specified for -p");
                        break;
                    case "-a":
                        appendMode = true;
                        break;
                    case "-s":
                        if (fullStatistics) {
                            throw new IllegalArgumentException("You can't use -s and -f at the same time.");
                        }
                        shortStatistics = true;
                        break;
                    case "-f":
                        if (shortStatistics) {
                            throw new IllegalArgumentException("You can't use -s and -f at the same time.");
                        }
                        fullStatistics = true;
                        break;
                }
            } else {
                inputFiles.add(arg);
            }
        }

        if (inputFiles.isEmpty()) {
            throw new IllegalArgumentException("No input files specified");
        }

        return new Config(outputPath, filePrefix, appendMode, shortStatistics, fullStatistics, inputFiles);
    }

    private String getRequiredValue(String[] args, int currentIndex, String errorMessage) {
        if (currentIndex + 1 >= args.length || args[currentIndex + 1].startsWith("-")) {
            throw new IllegalArgumentException(errorMessage);
        }
        return args[currentIndex + 1];
    }

    private boolean isLikelyFilename(String arg) {
        return arg.contains(".") &&
                !arg.matches("^-\\w+$") &&
                !arg.matches("^-\\d+$");
    }
}