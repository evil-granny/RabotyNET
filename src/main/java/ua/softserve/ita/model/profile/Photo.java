package ua.softserve.ita.model.profile;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "photo")
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Photo {

    @Id
    @GeneratedValue
    @Column(name = "photo_id")
    private Long photoId;

    @Column(name = "name")
    private String name;

    @Transient
    private String base64Image;

}
