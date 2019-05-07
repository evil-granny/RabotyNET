package ua.softserve.ita.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Letter {

    String eMail;

    String subject;

    String content;

    String linkForAttachment;

    String attachmentFileName;

    boolean withAttachment;

}
