package ua.softserve.ita.model.enumtype;

public enum Status {

    CREATED("created"),

    MAIL_SENT("mail_sent"),

    APPROVED("approved"),

    BLOCKED("blocked");

    private String type;

    Status(String code) {
        this.type = code;
    }

    public String getCode() {
        return type;
    }

    public static Status fromCode(String code) {
        for (Status type : Status.values()) {
            if (type.getCode().equals(code)) {
                return type;
            }
        }

        throw new UnsupportedOperationException(
                "The code " + code + " is not supported!");
    }

}
