package birintsev.artplace.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Timestamp;
import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PublicationDTO {

    private UUID id;

    private String title;

    private String publicationText;

    private PublicDTO parentPublic;

    private Set<FileDTO> attachments;

    private Timestamp publicationDate;
}
