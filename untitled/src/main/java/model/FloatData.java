package model;

import java.util.Objects;

public class FloatData extends DataEntry {
    private final double value;

    public FloatData(double value) {
        this.value = value;
    }

    @Override
    public DataType getType() { return DataType.FLOAT; }
    @Override
    public String getValueAsString() { return String.valueOf(value); }
}
