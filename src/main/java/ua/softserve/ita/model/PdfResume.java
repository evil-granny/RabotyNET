package ua.softserve.ita.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pdf_resume")
public class PdfResume implements Serializable {

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


}
