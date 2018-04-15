package messages;

import crew.Worker;
import operations.Operation;

import java.io.Serializable;

public class DoctorRequest extends SerializationWrapper implements Serializable  {
    private final Worker doctor;
    private final String patientName;
    private final Operation operation;

    public DoctorRequest(Worker doctor, String patientName, Operation operation) {
        this.doctor = doctor;
        this.patientName = patientName;
        this.operation = operation;
    }

    public String makeRoutingKey() {
        return String.join(".", operation.operationName(), patientName, doctor.getName());
    }

    public static String makeRoutingKey(String operationName, String patientName, String doctorName) {
        return String.join(".", operationName, patientName, doctorName);
    }

    @Override
    public String toString() {
        return String.format("Badanie: %s. Pacjent: %s. Zlecił: %s.", operation, patientName, doctor);
    }

    Worker getDoctor() {
        return doctor;
    }
}
