package ua.softserve.ita.model.enumtype;

public enum Employment {

    FULL("full"),

    PART_TIME("part_time"),

    HOURLY("hourly"),

    TRAINEE("trainee");

    private String type;

    Employment(String code) {
        this.type = code;
    }

    public String getCode() {
        return type;
    }

    public static Employment fromCode(String code) {
        for (Employment type : Employment.values()) {
            if (type.getCode().equals(code)) {
                return type;
            }
        }

        throw new UnsupportedOperationException(
                "The code " + code + " is not supported!");
    }

}
