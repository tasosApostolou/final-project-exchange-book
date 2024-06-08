package com.example.changebook.repository;

import com.example.changebook.model.Store;
import com.example.changebook.model.StoreBook;
import com.example.changebook.model.StoreBookId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StoreBooksRepository extends JpaRepository<StoreBook, StoreBookId> {
    @Override
    List<StoreBook> findAll();
    List<StoreBook> findStoreBooksByStore(Store store);
    List<StoreBook> findByBook_Title(String title);
    @Query("SELECT sb FROM StoreBook sb WHERE sb.book.title = :title")
    List<StoreBook> findStoreBooksByBookTitle(@Param("title") String title);
}
