package com.example.changebook.model;
import com.example.changebook.model.Identity.AbstractEntity;
import jakarta.persistence.*;
import lombok.*;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "store")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Store extends AbstractEntity {
    @Column
    private String name;

    @Column
    private String address;

    @OneToMany(mappedBy = "store", cascade = CascadeType.REMOVE)
    private Set<StoreBook> storeBooks = new HashSet<>();

    @OneToOne(cascade = CascadeType.ALL,orphanRemoval = true)
    @JoinColumn(name = "user_id",referencedColumnName = "id",unique = true)
    private User user;

    public Store(String name, String address) {
        this.name = name;
        this.address = address;
    }
    public void addUser(User user) {
        this.user = user;
        user.setStore(this);
    }
    public void addBook(StoreBook storeBook){
        this.storeBooks.add(storeBook);
        storeBook.setStore(this);
//        storeBook.getBook().getStoreBooks().add(storeBook);
//        storeBook.getBook().addStore(storeBook);
    }
    public Set<StoreBook> getAllBooks() {
        return Collections.unmodifiableSet(storeBooks);
    }

}
