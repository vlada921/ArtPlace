package birintsev.artplace.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.net.URI;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileDTO {

    private UUID id;

    private URI uri;

    private String name;
}
