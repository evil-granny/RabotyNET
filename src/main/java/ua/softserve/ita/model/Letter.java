package ua.softserve.ita.model;

public class Letter {

    String eMail;
    String subject;
    String content;
    String linkForAttachment;
    boolean withAttachment;

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLinkForAttachment() {
        return linkForAttachment;
    }

    public void setLinkForAttachment(String linkForAttachment) {
        this.linkForAttachment = linkForAttachment;
    }

    public boolean isWithAttachment() {
        return withAttachment;
    }

    public void setWithAttachment(boolean withAttachment) {
        this.withAttachment = withAttachment;
    }
}
