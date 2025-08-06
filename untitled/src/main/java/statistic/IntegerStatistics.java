package statistic;

import model.DataEntry;
import model.IntegerData;

public class IntegerStatistics extends BaseStatistics {
    long min = Long.MAX_VALUE;
    long max = Long.MIN_VALUE;
    long sum = 0;

    public IntegerStatistics(boolean fullStatistics) {
        super(fullStatistics);
    }

    @Override
    public void collect(DataEntry entry) {
        long value = ((IntegerData) entry).getValue();
        count++;
        if (fullStatistics) {
            min = Math.min(min, value);
            max = Math.max(max, value);
            sum += value;
        }
    }

    @Override
    protected void printAdditionalStats() {
        System.out.printf("Minimum value: %d%n", min);
        System.out.printf("Maximum value: %d%n", max);
        System.out.printf("Sum: %d%n", sum);
        System.out.printf("Average value: %.2f%n", (double) sum / count);
    }
}
