package com.example.changebook.model;

import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
public class StoreBookId implements Serializable {
    private Long store;
    private Long book;

    public StoreBookId() {
    }

    public StoreBookId(Long store, Long book) {
        this.store = store;
        this.book = book;
    }

    // Getters and setters

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StoreBookId that)) return false;

        if (!store.equals(that.store)) return false;
        return book.equals(that.book);
    }

    @Override
    public int hashCode() {
        int result = store.hashCode();
        result = 31 * result + book.hashCode();
        return result;
    }
}

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (!(o instanceof StoreBookId)) return false;
//        StoreBookId that = (StoreBookId) o;
//        return Objects.equals(getStore(), that.getStore()) &&
//                Objects.equals(getBook(), that.getBook());
//    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;  // checks if the objects are exactly the same instance
//        if (o == null || getClass() != o.getClass()) return false;  // checks if `o` is not null and is of the same class
//
//        StoreBookId that = (StoreBookId) o;  // Typecasts `o` to `StoreBookId` so that fields can be accessed
//
//        // Compares the `store` and `book` fields for equality
//        return Objects.equals(store, that.store) &&
//                Objects.equals(book, that.book);
//    }

//    @Override
//    public int hashCode() {
//        return Objects.hash(getStore(), getBook());
//    }

