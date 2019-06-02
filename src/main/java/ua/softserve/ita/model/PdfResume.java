package ua.softserve.ita.model;

import lombok.*;
import ua.softserve.ita.model.profile.Person;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pdf_resume")
@NamedQueries({
        @NamedQuery(name = PdfResume.FIND_BY_USER_ID, query = "select pdfResume from PdfResume pdfResume where pdfResume.person.userId = :id"),
        @NamedQuery(name = PdfResume.FIND_BY_PDF_NAME, query = "select pdfResume from PdfResume pdfResume where pdfResume.pdfName = :name"),
})
public class PdfResume implements Serializable {

    public static final String FIND_BY_USER_ID = "PdfResume.findByUserId";
    public static final String FIND_BY_PDF_NAME = "PdfResume.findByPdfName";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pdf_id")
    private Long pdfId;

    @Column(name = "path", length = 500)
    @Size(max = 500, message = "path length is incorrect")
    private String path;

    @Column(name = "pdf_name", length = 100)
    @Size(max = 100, message = "pdf_name length is incorrect")
    private String pdfName;

    @OneToOne
    @NotNull(message = "person must be not null")
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    private Person person;

}
