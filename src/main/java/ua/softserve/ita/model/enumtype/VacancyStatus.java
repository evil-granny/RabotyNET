package ua.softserve.ita.model.enumtype;

public enum VacancyStatus {

    OPEN("open"),

    OUTDATED("outdated"),

    OCCUPIED("occupied");

    private String type;

    VacancyStatus(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

}
