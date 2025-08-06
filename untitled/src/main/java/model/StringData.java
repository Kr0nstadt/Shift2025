package model;

import java.util.Objects;

public class StringData extends DataEntry {
    private final String value;

    public StringData(String value) {
        this.value = value;
    }

    @Override
    public DataType getType() { return DataType.STRING; }
    @Override
    public String getValueAsString() { return value; }

}