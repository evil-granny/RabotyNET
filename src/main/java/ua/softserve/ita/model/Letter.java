package ua.softserve.ita.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.File;

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
