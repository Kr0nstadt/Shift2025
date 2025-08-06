package org.example;

import config.Config;
import config.ConfigParser;
import processing.DataProcessor;

public class Main {
    public static void main(String[] args) {
        try {
            Config config = new ConfigParser(args).parse();
            DataProcessor processor = new DataProcessor(config);
            processor.process();
        } catch (IllegalArgumentException e) {
            System.err.println("Error in arguments: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("An error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }
}