package utilities;

import com.rabbitmq.client.Connection;
import crew.*;
import operations.Operation;


public class WorkerFactory {

    public static Worker getType(String type, String skill1, String skill2) {
        String lowerCaseType = type.toLowerCase();
        switch (lowerCaseType) {
            case "doc":
                return new Doctor();
            case "tech":
                Operation firstSkill = SkillFactory.getType(skill1);
                Operation secondSkill = SkillFactory.getType(skill2);
                return new Technician(firstSkill, secondSkill);
            case "admin":
                return new Admin();
            default:
                return new UnknownWorker();
        }
    }
}
