package birintsev.artplace.model.db;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.sql.Date;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "ap_users")
public class User implements UserDetails {

    @Id
    @GeneratedValue
    private UUID id;

    private String name;

    private String email;

    private Date birthday;

    private String gender;

    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "ap_user_authorities",
        joinColumns = {
            @JoinColumn(name = "user_id", referencedColumnName = "id")
        },
        inverseJoinColumns = {
            @JoinColumn(name = "authority_name", referencedColumnName = "name")
        }
    )
    private Set<Authority> authorities = new HashSet<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return registrationConfirmed();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return registrationConfirmed();
    }

    public void addAuthority(GrantedAuthority authority) {
        authorities.add(new Authority(authority.getAuthority()));
    }

    private boolean registrationConfirmed() {
        return authorities.contains(Authority.REG_CONFIRMED);
    }
}
