package statistic;

import model.DataEntry;
import model.FloatData;

public class FloatStatistics extends BaseStatistics {
    private double min = Double.MAX_VALUE;
    private double max = Double.MIN_VALUE;
    private double sum = 0;

    public FloatStatistics(boolean fullStatistics) {
        super(fullStatistics);
    }

    @Override
    public void collect(DataEntry entry) {
        double value = ((FloatData) entry).getValue();
        count++;
        if (fullStatistics) {
            min = Math.min(min, value);
            max = Math.max(max, value);
            sum += value;
        }
    }

    @Override
    protected void printAdditionalStats() {
        System.out.printf("Minimum value: %.2f%n", min);
        System.out.printf("Maximum value: %.2f%n", max);
        System.out.printf("Sum: %.2f%n", sum);
        System.out.printf("Average value: %.2f%n", sum / count);
    }
}

