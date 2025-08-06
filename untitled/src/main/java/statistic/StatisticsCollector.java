package statistic;

import model.*;

import java.util.*;
import java.util.stream.Collectors;

public class StatisticsCollector {
    private final boolean shortStats;
    private final boolean fullStats;

    public StatisticsCollector(boolean shortStats, boolean fullStats) {
        this.shortStats = shortStats;
        this.fullStats = fullStats;
    }

    public void printStatistics(List<DataEntry> entries) {
        if (!shortStats && !fullStats) return;

        Map<DataType, List<DataEntry>> grouped = entries.stream()
                .collect(Collectors.groupingBy(DataEntry::getType));

        for (DataType type : grouped.keySet()) {
            List<DataEntry> typeEntries = grouped.get(type);
            System.out.println("\nStatistics for " + type.toString().toLowerCase() + ":");
            System.out.println("Number of elements: " + typeEntries.size());

            if (fullStats) {
                switch (type) {
                    case INTEGER:
                        printIntegerStats(typeEntries);
                        break;
                    case FLOAT:
                        printFloatStats(typeEntries);
                        break;
                    case STRING:
                        printStringStats(typeEntries);
                        break;
                }
            }
        }
    }

    private void printIntegerStats(List<DataEntry> entries) {
        LongSummaryStatistics stats = entries.stream()
                .mapToLong(e -> ((IntegerData) e).getValue())
                .summaryStatistics();

        System.out.println("Minimum value: " + stats.getMin());
        System.out.println("Maximum value: " + stats.getMax());
        System.out.println("Sum: " + stats.getSum());
        System.out.println("Average: " + stats.getAverage());
    }

    private void printFloatStats(List<DataEntry> entries) {
        DoubleSummaryStatistics stats = entries.stream()
                .mapToDouble(e -> ((FloatData) e).getValue())
                .summaryStatistics();

        System.out.println("Minimum value: " + stats.getMin());
        System.out.println("Maximum value: " + stats.getMax());
        System.out.println("Sum: " + stats.getSum());
        System.out.println("Average: " + stats.getAverage());
    }

    private void printStringStats(List<DataEntry> entries) {
        IntSummaryStatistics stats = entries.stream()
                .mapToInt(e -> ((StringData) e).getValueAsString().length())
                .summaryStatistics();

        System.out.println("Length of the shortest line: " + stats.getMin());
        System.out.println("Length of the longest line: " + stats.getMax());
        System.out.println("Average line length: " + stats.getAverage());
    }
}