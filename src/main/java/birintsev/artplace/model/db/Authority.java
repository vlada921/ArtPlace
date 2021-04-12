package birintsev.artplace.model.db;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "ap_authorities")
public class Authority implements GrantedAuthority {

    public static final Authority REG_CONF_PENDING =
        new Authority("REGISTRATION_CONFIRMATION_PENDING");

    public static final Authority REG_CONFIRMED =
        new Authority("REGISTRATION_CONFIRMED");

    @Id
    private String name;

    @Override
    public String getAuthority() {
        return name;
    }
}
