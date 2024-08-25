package com.example.changebook.model;

import com.example.changebook.model.Identity.AbstractEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
//@ToString
public class Book extends AbstractEntity {
    @Column()
    private String title;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;

    public Book(Long id,String title, Author author) {
        this.setId(id);
        this.title = title;
        this.author = author;
    }

    @Getter (AccessLevel.PROTECTED)
    @ManyToMany(mappedBy = "books")
    private Set<Person> persons = new HashSet<>();

    @OneToMany(mappedBy = "book")
    private Set<StoreBook> storeBooks = new HashSet<>();

    @OneToMany(mappedBy = "book")
    private Set<Notification> notifications;
    public void addPerson(Person person){
        this.persons.add(person);
        person.getBooks().add(this);
    }
    public void addStore(StoreBook storeBook){
        this.storeBooks.add(storeBook);
        storeBook.setBook(this);

    }
    public void addAuthor(Author author){
        this.setAuthor(author);
        author.getBooks().add(this);
    }

    public Boolean isTheSameBook(Book book){ // lets say that if a title book and the name of autho is the same then book also the same
        if (book==null){
            return false;
        }
        if (book.title.equals(this.title) && book.author.getName().equals(this.author.getName()) ){
            return true;
        }else {
            return false;
        }
    }

    public Set<Person> getAllPersons() {
        return Collections.unmodifiableSet(persons);
    }

    @Override
    public String toString() {
        return "Book{" +
                "author=" + this.author.getName() +
                '}';
    }
}
