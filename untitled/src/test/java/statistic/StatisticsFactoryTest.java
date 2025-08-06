package statistic;

import static org.junit.jupiter.api.Assertions.*;

import model.DataType;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class StatisticsFactoryTest {

    @Test
    void testCreateIntegerStatistics() {
        Statistics stats = StatisticsFactory.createStatistics(DataType.INTEGER, true);
        assertTrue(stats instanceof IntegerStatistics);
    }

    @Test
    void testCreateFloatStatistics() {
        Statistics stats = StatisticsFactory.createStatistics(DataType.FLOAT, false);
        assertTrue(stats instanceof FloatStatistics);
    }

    @Test
    void testCreateStringStatistics() {
        Statistics stats = StatisticsFactory.createStatistics(DataType.STRING, true);
        assertTrue(stats instanceof StringStatistics);
    }
}