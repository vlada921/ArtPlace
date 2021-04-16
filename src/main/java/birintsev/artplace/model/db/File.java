package birintsev.artplace.model.db;

import lombok.Data;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.net.URI;
import java.util.UUID;

@Data
@Entity
@Table(name = "ap_files")
public class File {

    @Id
    private UUID id;

    private URI uri;

    private String name;
}
