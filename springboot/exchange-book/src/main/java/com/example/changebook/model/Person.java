package com.example.changebook.model;

import com.example.changebook.model.Identity.AbstractEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

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

//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private Long id;
    @Column(length = 50)
    private String firstname;

    @Column(length = 50)
    private String lastname;

    @Column (length = 15)
    private String birthdate;

    @Column(length = 20)
    private String city;

//    @ManyToOne
//    @JoinColumn(name = "city_id")
//    private Cities city;

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

    public Person(Long id, String firstname, String lastname, String gender, String birthdate, String city, User user) {
        this.setId(id);
        this.firstname = firstname;
        this.lastname = lastname;
        this.city = city;
        this.user = user;
    }

    public Set<Book> getAllBooks() {
        return Collections.unmodifiableSet(books);
    }
    public void addUser(User user) {
        this.user = user;
        user.setPerson(this);
    }
}