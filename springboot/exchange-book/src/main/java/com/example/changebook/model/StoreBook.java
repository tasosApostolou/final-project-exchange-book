package com.example.changebook.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@IdClass(StoreBookId.class)
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StoreBook {
    @Id
    @ManyToOne()
    @JoinColumn(name = "store_id")
    private Store store;

    @Id
    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    private double price;


    public void addBook(Book book){
        this.setBook(book);
        book.getStoreBooks().add(this);
    }
    public void addStore(Store store){
        this.setStore(store);
        store.getStoreBooks().add(this);
    }
}


