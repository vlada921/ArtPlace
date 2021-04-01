package birintsev.artplace.model.db;

import lombok.Data;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Data
@Entity
@Table(name = "ap_users")
public class User {

    @Id
    @GeneratedValue
    private UUID id;

    private String name;

    private int age;

    private String gender;
}
