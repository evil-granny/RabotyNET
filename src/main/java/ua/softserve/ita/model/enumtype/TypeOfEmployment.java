package ua.softserve.ita.model.enumtype;

public enum TypeOfEmployment {

    FULL("full"),

    PART_TIME("part_time"),

    HOURLY("hourly"),

    TRAINEE("trainee");

    private String type;

    TypeOfEmployment(String code) {
        this.type = code;
    }

    public String getCode() {
        return type;
    }

    public static TypeOfEmployment fromCode(String code) {
        for (TypeOfEmployment type : TypeOfEmployment.values()) {
            if (type.getCode().equals(code)) {
                return type;
            }
        }
        throw new UnsupportedOperationException(
                "The code " + code + " is not supported!");
    }

}
