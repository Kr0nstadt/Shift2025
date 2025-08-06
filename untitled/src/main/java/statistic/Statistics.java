package statistic;

import model.DataEntry;

public interface Statistics {
    void collect(DataEntry entry);
    void printStatistics();
}
