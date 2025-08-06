package valid;

import model.DataEntry;
import model.StringData;

public class StringValidator implements DataValidator {
    @Override
    public boolean isValid(String value) {
        return true;
    }
    @Override
    public DataEntry parse(String value) {
        return new StringData(value);
    }
}
