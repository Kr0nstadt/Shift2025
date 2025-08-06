package statistic;

import model.DataEntry;
import model.StringData;

public class StringStatistics extends BaseStatistics {
    int minLength = Integer.MAX_VALUE;
    int maxLength = 0;
    int totalLength = 0;

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
        System.out.printf("Length of the shortest string: %d%n", minLength);
        System.out.printf("Length of the longest string: %d%n", maxLength);
        System.out.printf("Average string length: %.2f%n", (double) totalLength / count);
    }
}