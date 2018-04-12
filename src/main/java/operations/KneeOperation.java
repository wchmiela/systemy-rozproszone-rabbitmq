package operations;

import java.io.Serializable;

public class KneeOperation implements Operation, Serializable {
    @Override
    public String toString() {
        return "operacja kolana";
    }

    @Override
    public String operationName() {
        return "knee";
    }
}
