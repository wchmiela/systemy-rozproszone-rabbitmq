package crew;

import java.io.Serializable;

public class TechnicianReply implements Serializable {

    private final DoctorRequest request;
    private final Worker technician;

    public TechnicianReply(DoctorRequest request, Worker technician) {
        this.request = request;
        this.technician = technician;
    }

    @Override
    public String toString() {
        return String.format("%s Wykonal: %s", request, technician.getName());
    }
}
