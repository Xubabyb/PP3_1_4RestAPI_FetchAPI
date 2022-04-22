package ru.kata.spring.boot_security.demo.model;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.Collection;

@Entity
@Data
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Length(message = "Name should not be between 2 and 20 characters", min = 2, max = 20)
    @NotEmpty(message = "Field first name cannot be empty")
    @Column(name = "name")
    private String firstName;
    @Length(message = "Last name should not be between 2 and 30 characters", min = 2, max = 30)
    @NotEmpty(message = "Field last name cannot be empty")
    private String lastName;
    @NotEmpty(message = "Field mail cannot be empty")
    @Email(message = "Email should be valid")
    private String email;
    private String password;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Collection<Role> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
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
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public UserDetails fromUser() {
        return new org.springframework.security.core.userdetails.User(email, password, getAuthorities());
    }
}
