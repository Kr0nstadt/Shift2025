package statistic;

public abstract class BaseStatistics implements Statistics {
    protected int count = 0;
    protected final boolean fullStatistics;

    protected BaseStatistics(boolean fullStatistics) {
        this.fullStatistics = fullStatistics;
    }

    @Override
    public void printStatistics() {
        System.out.printf("Number of elements: %d%n", count);
        if (fullStatistics) {
            printAdditionalStats();
        }
    }

    protected abstract void printAdditionalStats();
}