package statistic;

import model.DataEntry;
import model.DataType;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class StatisticsCollector {
    private final boolean shortStatistics;
    private final boolean fullStatistics;
    private final Map<DataType, Statistics> statisticsMap;

    public StatisticsCollector(boolean shortStatistics, boolean fullStatistics) {
        this.shortStatistics = shortStatistics;
        this.fullStatistics = fullStatistics;
        this.statisticsMap = new EnumMap<>(DataType.class);
    }

    public void printStatistics(List<DataEntry> entries) {
        if (!shortStatistics && !fullStatistics) {
            return;
        }

        for (DataEntry entry : entries) {
            DataType type = entry.getType();
            statisticsMap.computeIfAbsent(type,
                            t -> StatisticsFactory.createStatistics(t, fullStatistics))
                    .collect(entry);
        }

        for (Map.Entry<DataType, Statistics> entry : statisticsMap.entrySet()) {
            System.out.println("\nStatistics for type " + entry.getKey() + ":");
            entry.getValue().printStatistics();
        }
    }
}
