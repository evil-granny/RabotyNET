package ua.softserve.ita.model.enumtype;

public enum Extension {

    JPG("jpg"),

    JPEG("jpeg"),

    PNG("png");

    private String type;

    Extension(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

}
