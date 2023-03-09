package dev.be.sns.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import dev.be.sns.model.Entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.catalina.UserDatabase;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;

@AllArgsConstructor
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class User implements UserDetails {
    private Integer id;
    private UserRole userRole;
    private String username;
    private String password;
    private Timestamp registeredAt;
    private Timestamp updatedAt;
    private Timestamp deletedAt;


    public static User fromEntity(UserEntity entity)
    {
        return new User(
                entity.getId(),
                entity.getRole(),
                entity.getUserName(),
                entity.getPassword(),
                entity.getRegisteredAt(),
                entity.getUpdatedAt(),
                entity.getDeletedAt()
        );
    }

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.getUserRole().toString()));
    }

    @Override
    @JsonIgnore
    public String getUsername() {
        return this.username;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return this.deletedAt==null;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return this.deletedAt==null;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return this.deletedAt==null;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return this.deletedAt==null;
    }
}
