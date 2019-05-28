package ua.softserve.ita.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ua.softserve.ita.model.Resume;

@Getter
@Setter
@NoArgsConstructor
public class SkillDTO {
    private Long skillId;
    private String title;
    private String description;
    private Resume resume;
}
