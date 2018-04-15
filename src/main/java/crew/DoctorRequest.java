package crew;

import operations.Operation;

import java.io.Serializable;

class DoctorRequest extends SerializationWrapper implements Serializable  {
    private final Worker doctor;
    private final String patientName;
    private final Operation operation;

    DoctorRequest(Worker doctor, String patientName, Operation operation) {
        this.doctor = doctor;
        this.patientName = patientName;
        this.operation = operation;
    }

    String makeRoutingKey() {
        return String.join(".", operation.operationName(), patientName, doctor.getName());
    }

    static String makeRoutingKey(String operationName, String patientName, String doctorName) {
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
