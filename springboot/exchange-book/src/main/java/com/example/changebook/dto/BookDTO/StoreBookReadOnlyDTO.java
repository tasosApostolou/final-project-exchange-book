package com.example.changebook.dto.BookDTO;

import com.example.changebook.dto.BaseDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StoreBookReadOnlyDTO {
//    public BookReadOnlyDTO book;
//    public StoreReadOnlyDTO store;
    public Long storeId;
    public Long bookId;
    public Double price;
}
