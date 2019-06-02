package ua.softserve.ita.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "claim")
@NamedQueries({
        @NamedQuery(name = Claim.FIND_BY_COMPANY_ID, query = "select claim from Claim claim where claim.company.companyId = :id order by claim.claimId"),
})
public class Claim implements Serializable {

    public final static String FIND_BY_COMPANY_ID = "Claim.findByCompanyId";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "claim_id")
    private long claimId;

    @Column(name = "title", nullable = false, length = 50)
    @NotNull
    @NotBlank
    private String title;

    @Column(name = "description", length = 500)
    @NotNull
    @NotBlank
    @Pattern(regexp = "^[\\s\\S]{0,500}$")
    private String description;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    @NotNull
    private User user;

    @ManyToOne
    @JoinColumn(name = "company_id", referencedColumnName = "company_id", nullable = false)
    @NotNull
    private Company company;

    @Override
    public String toString() {
        return "Claim{" +
                "claimId=" + claimId +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", user=" + user +
                '}';
    }

}
