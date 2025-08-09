package org.example;

import config.Config;
import config.ConfigParser;
import processing.DataProcessor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
    public static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        try {
            logger.info("Starting application with arguments: {}", (Object) args);

            Config config = parseConfig(args);
            DataProcessor processor = new DataProcessor(config);
            processor.process();

            logger.info("Application finished successfully");
        } catch (IllegalArgumentException e) {
            logger.error("Invalid arguments: {}", e.getMessage());
            printUsage();
        } catch (Exception e) {
            logger.error("Unexpected error occurred", e);
        }
    }

    private static Config parseConfig(String[] args) {
        try {
            return new ConfigParser(args).parse();
        } catch (IllegalArgumentException e) {
            logger.debug("Config parsing failed", e);
            throw e;
        }
    }

    private static void printUsage() {
        System.out.println("Usage: java -jar app.jar [options] input_files...");
        System.out.println("Options:");
        System.out.println("  -o <path>    Output directory");
        System.out.println("  -p <prefix>  File prefix for output");
        System.out.println("  -a           Append mode");
        System.out.println("  -s           Short statistics");
        System.out.println("  -f           Full statistics");
        System.out.println("  --           Treat all following arguments as files");
    }
}