package ua.softserve.ita.model.enumtype;

public enum Currency {

    USD("usd"),

    UAH("uah"),

    EUR("eur");

    private String type;

    Currency(String code) {
        this.type = code;
    }

    public String getCode() {
        return type;
    }

    public static Currency fromCode(String code) {
        for (Currency type : Currency.values()) {
            if (type.getCode().equals(code)) {
                return type;
            }
        }

        throw new UnsupportedOperationException(
                "The code " + code + " is not supported!");
    }

}
