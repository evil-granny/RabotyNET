package ua.softserve.ita.model;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "status")
public class Status {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "status_id")
    private long statusId;

    @Column(name = "approved")
    private boolean approved;

    @Column(name = "mailSent")
    private boolean mailSent;

    @Column(name = "reliable")
    private boolean reliable;

    public long getStatusId() {
        return statusId;
    }

    public boolean isReadyToDelete() {
        return approved && mailSent && reliable;
    }
}
