package ua.com.model.enumtype;

import java.util.Arrays;

public enum Employment {

    FULL("full", "Full time"),

    PART_TIME("part_time", "Part time"),

    HOURLY("hourly", "Hourly"),

    TRAINEE("trainee", "Trainee");

    private String type;
    private String displayValue;

    Employment(String type, String displayValue) {
        this.type = type;
        this.displayValue = displayValue;
    }

    public String getType() {
        return type;
    }

    public static Employment fromCode(String type) {
        return Arrays.stream(Employment.values())
                .filter(employment -> employment.getType().equals(type))
                .findFirst()
                .orElseThrow(() ->  new UnsupportedOperationException("The type " + type + " is not supported!"));
    }

}
