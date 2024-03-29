package ua.com.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Letter {

    String emailAddress;

    String subject;

    String content;

    String linkForAttachment;

    String attachmentFileName;

    boolean withAttachment;

}
