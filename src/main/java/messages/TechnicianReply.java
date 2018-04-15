package messages;

import crew.Worker;

import java.io.Serializable;

public class TechnicianReply extends SerializationWrapper implements Serializable {

    private final DoctorRequest request;
    private final Worker technician;

    public TechnicianReply(DoctorRequest request, Worker technician) {
        this.request = request;
        this.technician = technician;
    }

    public String makeRoutingKey() {
        return request.getDoctor().getName();
    }

    @Override
    public String toString() {
        return String.format("%s Wykonal: %s.", request, technician.getName());
    }
}
