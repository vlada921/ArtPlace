package birintsev.artplace.dto;

import birintsev.artplace.model.db.Authority;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDTO {

    private UUID id;

    private String name;

    private String email;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthday;

    private String gender;

    private String password;

    private Set<Authority> authorities = new HashSet<>();
}
