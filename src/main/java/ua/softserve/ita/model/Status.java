package ua.softserve.ita.model;

import javax.persistence.*;

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

    public boolean isReadToDelete() {
        return approved && mailSent && reliable;
    }

    public void setStatusId(long statusId) {
        this.statusId = statusId;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public boolean isMailSent() {
        return mailSent;
    }

    public void setMailSent(boolean mailSent) {
        this.mailSent = mailSent;
    }

    public boolean isReliable() {
        return reliable;
    }

    public void setReliable(boolean reliable) {
        this.reliable = reliable;
    }
}
