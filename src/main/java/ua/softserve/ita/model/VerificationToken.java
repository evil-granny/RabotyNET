package ua.softserve.ita.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

@Data
@Entity
@NamedQueries({
        @NamedQuery(name = VerificationToken.FIND_TOKEN_BY_USER, query = "select vt from VerificationToken vt where vt.user.userId = :id"),
        @NamedQuery(name = VerificationToken.FIND_VERIFICATION_TOKEN, query = "select vt from VerificationToken vt where vt.token = :token")
})
public class VerificationToken {

    private static final int EXPIRATION = 60 * 24;

    public static final String FIND_TOKEN_BY_USER = "VerificationToken.findTokenByUser";
    public static final String FIND_VERIFICATION_TOKEN = "VerificationToken.findVerificationToken";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String token;

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id", foreignKey = @ForeignKey(name = "FK_VERIFY_USER"))
    private User user;

    private Date expiryDate;

    public VerificationToken() {
    }

    public VerificationToken(final String token, final User user) {
        this.token = token;
        this.user = user;
        this.expiryDate = calculateExpiryDate();
    }

    private Date calculateExpiryDate() {
        final Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(new Date().getTime());
        cal.add(Calendar.MINUTE, VerificationToken.EXPIRATION);
        return new Date(cal.getTime().getTime());
    }

}
