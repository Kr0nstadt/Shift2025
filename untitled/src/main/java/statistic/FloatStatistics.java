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
        System.out.printf("Минимальное значение: %.2f%n", min);
        System.out.printf("Максимальное значение: %.2f%n", max);
        System.out.printf("Сумма: %.2f%n", sum);
        System.out.printf("Среднее значение: %.2f%n", sum / count);
    }
}

