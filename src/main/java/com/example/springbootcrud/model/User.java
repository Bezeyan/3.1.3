package com.example.springbootcrud.model;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "users")

public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "user_lastname")
    private String userLastName;

    @Column(name = "user_password")
    private String password;

    @Column(name = "age")
    private Byte age;

    @Column(name = "user_email")
    private String email;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    @JoinTable(
            name = "user_role",
            joinColumns = { @JoinColumn(name = "user_id") },
            inverseJoinColumns = { @JoinColumn(name = "role_id") }
    )
    private Set<Role> roles = new HashSet<>();

    public User() {}

    public User(String userName, String userLastName, String password,Set<Role> roles ) {
        this.userName = userName;
        this.userLastName = userLastName;
        this.password = password;
        this.roles = roles;
    }

    public User(String userName, String userLastName, String password, Byte age, String email, Set<Role> roles) {
        this.userName = userName;
        this.userLastName = userLastName;
        this.password = password;
        this.age = age;
        this.email = email;
        this.roles = roles;
    }

    public Byte getAge() {
        return age;
    }

    public void setAge(Byte age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUserName(String userName) { this.userName = userName; }

    public void setUserLastName(String userLastName) { this.userLastName = userLastName; }

    public void setPassword(String password) { this.password = password; }

    public String getUserName() { return userName; }

    public String getUserLastName() { return userLastName; }

    public String getPassword() { return this.password; }

    public Set<Role> getRoles() { return roles; }

    public Long getId() {
        return id;
    }

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }
    @JsonIgnore
    @Override
    public String getUsername() {
        return this.userName;
    }
    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", userLastName='" + userLastName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id.equals(user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
