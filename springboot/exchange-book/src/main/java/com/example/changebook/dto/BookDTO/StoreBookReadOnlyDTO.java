package com.example.changebook.dto.BookDTO;

import com.example.changebook.dto.BaseDTO;
import com.example.changebook.dto.StoreDTO.StoreReadOnlyDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StoreBookReadOnlyDTO {
    public BookReadOnlyDTO book;
    public StoreReadOnlyDTO store;

    public Double price;
}
