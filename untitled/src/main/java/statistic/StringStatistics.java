package statistic;

import model.DataEntry;
import model.StringData;

public class StringStatistics extends BaseStatistics {
    private int minLength = Integer.MAX_VALUE;
    private int maxLength = 0;
    private int totalLength = 0;

    public StringStatistics(boolean fullStatistics) {
        super(fullStatistics);
    }

    @Override
    public void collect(DataEntry entry) {
        int length = ((StringData) entry).getValueAsString().length();
        count++;
        if (fullStatistics) {
            minLength = Math.min(minLength, length);
            maxLength = Math.max(maxLength, length);
            totalLength += length;
        }
    }

    @Override
    protected void printAdditionalStats() {
        System.out.printf("ƒлина самой короткой строки: %d%n", minLength);
        System.out.printf("ƒлина самой длинной строки: %d%n", maxLength);
        System.out.printf("—редн€€ длина строки: %.2f%n", (double) totalLength / count);
    }
}