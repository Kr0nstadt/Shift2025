package statistic;

import static org.junit.jupiter.api.Assertions.*;

import model.FloatData;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FloatStatisticsTest {

    @Test
    void testShortStatistics() {
        FloatStatistics stats = new FloatStatistics(false);

        stats.collect(new FloatData(1.5));
        stats.collect(new FloatData(2.5));

        assertEquals(2, stats.count);
    }

    @Test
    void testFullStatistics() {
        FloatStatistics stats = new FloatStatistics(true);

        stats.collect(new FloatData(1.1));
        stats.collect(new FloatData(2.2));
        stats.collect(new FloatData(3.3));

        assertEquals(3, stats.count);
        assertEquals(1.1, stats.min, 0.001);
        assertEquals(3.3, stats.max, 0.001);
        assertEquals(6.6, stats.sum, 0.001);
    }

    @Test
    void testMixedNumbers() {
        FloatStatistics stats = new FloatStatistics(true);

        stats.collect(new FloatData(-1.5));
        stats.collect(new FloatData(0.0));
        stats.collect(new FloatData(1.5));

        assertEquals(-1.5, stats.min, 0.001);
        assertEquals(1.5, stats.max, 0.001);
        assertEquals(0.0, stats.sum, 0.001);
    }

    @Test
    void testScientificNotation() {
        FloatStatistics stats = new FloatStatistics(true);
        stats.collect(new FloatData(1e3));
        stats.collect(new FloatData(2e3));

        assertEquals(1000.0, stats.min, 0.001);
        assertEquals(3000.0, stats.sum, 0.001);
    }
}