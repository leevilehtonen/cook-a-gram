package com.leevilehtonen.cookagram.domain;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "account")
public class Account extends AbstractPersistable<Long> {

    @Column(unique = true)
    @NotBlank
    @Length(min = 4, max = 32)
    private String username;

    @NotBlank
    @Length(min = 4, max = 64)
    private String password;

    @NotBlank
    @Length(min = 2, max = 32)
    private String firstname;

    @NotBlank
    @Length(min = 2, max = 32)
    private String lastname;

    @NotBlank
    @Email
    private String email;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Role> roles;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}
