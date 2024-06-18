package com.example.changebook.model;

import com.example.changebook.model.Identity.AbstractEntity;
import jakarta.persistence.*;
import lombok.*;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "person")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
//@ToString
public class Person extends AbstractEntity {

    @Column(length = 50)
    private String firstname;

    @Column(length = 50)
    private String lastname;

    @OneToOne(cascade = CascadeType.ALL,orphanRemoval = true)
    @JoinColumn(name = "user_id",referencedColumnName = "id",unique = true)
    private User user;

    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(
            name = "person_books",
            joinColumns = @JoinColumn(name = "person_id", referencedColumnName = "id",nullable = false),
            inverseJoinColumns = @JoinColumn(name = "book_id", referencedColumnName = "id",nullable = false)
    )
    private Set<Book> books = new HashSet<>();


    public Person(String firstname, String lastname) {
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public Person(Long id, String firstname, String lastname, User user) {
        this.setId(id);
        this.firstname = firstname;
        this.lastname = lastname;
        this.user = user;
    }

    public Set<Book> getAllBooks() {
        return Collections.unmodifiableSet(books);
    }
    public void addUser(User user) {
        this.user = user;
        user.setPerson(this);
    }

    @Override
    public String toString() {
        return "Person{" +
                "firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", username=" + this.user.getUsername() +
                '}';
    }
}
