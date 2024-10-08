package com.example.changebook.model;

import com.example.changebook.model.Identity.AbstractEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
//@RequiredArgsConstructor
//@ToString
public class User extends AbstractEntity implements UserDetails {

    @Column(length = 30, unique = true, nullable = false)
    private String username;

    @Column(length = 500)
    private String password;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;
    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToOne(mappedBy = "user")
    private Person person;
    @OneToOne(mappedBy = "user")
    private Store store;

    @OneToMany(mappedBy = "holderUSer", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH},orphanRemoval = true)
    @Getter(AccessLevel.PUBLIC)
    private Set<Notification> notifications;

    public User(Long id,String username, String password, Role role) {
        this.setId(id);
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public User(Long id,String username, Role role) {
        this.setId(id);
        this.username = username;
        this.role = role;
    }
    public Set<Notification> getAllNotifications(){
        return Collections.unmodifiableSet(notifications);
    }

    public static User NEW_PERSON(String username, String password) {
        User user = new User();
//        user.setIsActive(true);
        user.setRole(Role.PERSONAL);
        user.setStatus(Status.APPROVED);
        user.setUsername(username);
        user.setPassword(password);
        return user;
    }

    public static User NEW_STORE(String username, String password){
        User user = new User();
        user.setRole(Role.STORE);
        user.setStatus(Status.PENDING);
        user.setUsername(username);
        user.setPassword(password);
        return user;
    }

    public User(Long id){
        this.setIsActive(true);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
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
        return this.getIsActive() == null || this.getIsActive();
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                ", status=" + status +
                '}';
    }

}