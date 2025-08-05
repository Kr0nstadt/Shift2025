package model;

import java.util.Objects;

public class IntegerData extends DataEntry {
    private final long value;

    public IntegerData(long value) {
        this.value = value;
    }

    @Override
    public DataType getType() { return DataType.INTEGER; }
    @Override
    public String getValueAsString() { return String.valueOf(value); }
}