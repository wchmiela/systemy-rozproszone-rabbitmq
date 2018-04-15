package crew;

import java.io.Serializable;

class TechnicianReply extends SerializationWrapper implements Serializable {

    private final DoctorRequest request;
    private final Worker technician;

    TechnicianReply(DoctorRequest request, Worker technician) {
        this.request = request;
        this.technician = technician;
    }

    String makeRoutingKey() {
        return request.getDoctor().getName();
    }

    @Override
    public String toString() {
        return String.format("%s Wykonal: %s.", request, technician.getName());
    }
}
