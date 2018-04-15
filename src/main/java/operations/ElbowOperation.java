package operations;

import java.io.Serializable;

public class ElbowOperation implements Operation, Serializable {
    @Override
    public String operationName() {
        return "elbow";
    }

    @Override
    public String toString() {
        return "operacja łokcia";
    }
}
