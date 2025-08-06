package statistic;

import model.DataType;

public class StatisticsFactory {
    public static Statistics createStatistics(DataType type, boolean fullStatistics) {
        return switch (type) {
            case INTEGER -> new IntegerStatistics(fullStatistics);
            case FLOAT -> new FloatStatistics(fullStatistics);
            case STRING -> new StringStatistics(fullStatistics);
        };
    }
}
