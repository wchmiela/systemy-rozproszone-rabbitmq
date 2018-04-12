package utilities;

import operations.*;

public class SkillFactory {

    public static Operation getType(String skill) {
        String lowerCaseSkill = skill.toLowerCase();
        switch (lowerCaseSkill) {
            case "elbow":
                return new ElbowOperation();
            case "knee":
                return new KneeOperation();
            case "hip":
                return new HipOperation();
            default:
                return new UnknownOperation();
        }
    }
}
