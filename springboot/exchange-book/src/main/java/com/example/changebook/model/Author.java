package com.example.changebook.model;

import com.example.changebook.model.Identity.AbstractEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@ToString

@Entity
public class Author extends AbstractEntity {

  @Column(length = 100)
  private String name;

  @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
  @Getter(AccessLevel.PUBLIC)
  private Set<Book> books = new HashSet<>();


  public Author(Long id,String name, Set<Book> books) {
    this.setId(id);
    this.name = name;
    this.books = books;
  }

public void addBook(Book book){
    book.setAuthor(this);
    this.books.add(book);
}
}
