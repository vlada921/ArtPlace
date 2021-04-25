package birintsev.artplace.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PublicDTO {

    private UUID id;

    private UserDTO owner;

    private String name;
}
