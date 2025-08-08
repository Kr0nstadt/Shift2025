package statistic;

import static org.junit.jupiter.api.Assertions.*;

import model.DataEntry;
import model.FloatData;
import model.IntegerData;
import model.StringData;
import org.junit.jupiter.api.Test;
import java.util.List;


class StatisticsCollectorTest {

    @Test
    void testCollectMixedTypes() {
        StatisticsCollector collector = new StatisticsCollector(true, true);
        List<DataEntry> entries = List.of(
                new IntegerData(10),
                new FloatData(1.5),
                new StringData("test"),
                new IntegerData(20)
        );

        collector.printStatistics(entries);

        assertDoesNotThrow(() -> collector.printStatistics(entries));
    }

    @Test
    void testNoStatisticsWhenDisabled() {
        StatisticsCollector collector = new StatisticsCollector(false, false);
        List<DataEntry> entries = List.of(new IntegerData(10));

        collector.printStatistics(entries);

        assertDoesNotThrow(() -> collector.printStatistics(entries));
    }

    @Test
    void testShortStatisticsOnly() {
        StatisticsCollector collector = new StatisticsCollector(true, false);
        List<DataEntry> entries = List.of(
                new IntegerData(10),
                new IntegerData(20)
        );

        assertDoesNotThrow(() -> collector.printStatistics(entries));
    }
}