package ua.softserve.ita.model.profile;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "photo")
@Data
public class Photo {

    @Id
    @GeneratedValue
    @Column(name = "photo_id")
    private Long photoId;

    @Column(name = "name")
    private String name;

}
