package com.example.changebook.rest;

import com.example.changebook.dto.BookDTO.BookReadOnlyDTO;
import com.example.changebook.dto.BookDTO.StoreBookReadOnlyDTO;
import com.example.changebook.dto.StoreDTO.StoreReadOnlyDTO;
import com.example.changebook.dto.StoreDTO.StoreRegisterDTO;
import com.example.changebook.dto.StoreDTO.StoreUpdateDTO;
import com.example.changebook.dto.personDTO.PersonReadonlyDTO;
import com.example.changebook.dto.usersDTO.UserReadOnlyDTO;
import com.example.changebook.mapper.Mapper;
import com.example.changebook.model.Book;
import com.example.changebook.model.Person;
import com.example.changebook.model.Store;
import com.example.changebook.model.StoreBook;
import com.example.changebook.service.IStoreService;
import com.example.changebook.service.exceptions.EntityAlreadyExistsException;
import com.example.changebook.service.exceptions.EntityNotFoundException;
import com.example.changebook.validator.StoreRegisterValidator;
import com.example.changebook.validator.StoreUpdateValidator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/store")
@Slf4j
public class StoreRestController {
    private final IStoreService storeService;
    private final StoreUpdateValidator updateValidator;
    @Operation(summary = "Update a store(name and address")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "store updated",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserReadOnlyDTO.class)) }),
            @ApiResponse(responseCode = "401", description = "Unauthorized user",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid input was supplied",
                    content = @Content)})
    @PutMapping("/update/{id}")
    public ResponseEntity<StoreReadOnlyDTO> updateStore(@PathVariable("id") Long id, @RequestBody StoreUpdateDTO dto, BindingResult bindingResult) {
        if(!(dto.getId() ==id)){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        updateValidator.validate(dto, bindingResult);
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            Store store = storeService.updateStore(dto);
            StoreReadOnlyDTO readOnlyDTO = Mapper.mapToReadOnlyDTO(store);
            return new ResponseEntity<>(readOnlyDTO,HttpStatus.OK);
        }catch (EntityNotFoundException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "get all books of a store by store id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "books found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserReadOnlyDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Store user not found or store not have added books yet",
                    content = @Content)})
    @PreAuthorize("hasAuthority('PERSONAL') or hasAuthority('STORE')")
    @GetMapping("{storeId}/books")
    public ResponseEntity<List<StoreBookReadOnlyDTO>> getAllBooksByStoreId(@PathVariable Long storeId){
        List<StoreBook> books;
        List<StoreBookReadOnlyDTO> readOnlyDTOs = new ArrayList<>();
        try {
            books = storeService.getAllBooksByStoreId(storeId);

            for (StoreBook book : books) {
                readOnlyDTOs.add(Mapper.mapToReadOnlyDTO(book));
            }
            return new ResponseEntity<>(readOnlyDTOs, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>( HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Delete a Store by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Store Deleted",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StoreReadOnlyDTO.class)) }),
            @ApiResponse(responseCode = "404", description = "Store not found",
                    content = @Content)})

    @DeleteMapping("/{id}")
    public ResponseEntity<StoreReadOnlyDTO> deleteStore(@PathVariable("id") Long id){
        Store store;
        try {
            store = storeService.deleteStore(id);
            StoreReadOnlyDTO readOnlyDTO = Mapper.mapToReadOnlyDTO(store);
            return new ResponseEntity<>(readOnlyDTO,HttpStatus.OK);
        }catch (EntityNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "remove the from middleware ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "the book deleted from person list with books",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserReadOnlyDTO.class))}),
            @ApiResponse(responseCode = "404", description = "entity not found(person or book) searching by id in variable path ",
                    content = @Content)})
    @DeleteMapping("/{storeId}/books/{bookId}")
    public ResponseEntity<BookReadOnlyDTO> removeBookFromStore(@PathVariable Long storeId, @PathVariable Long bookId) {
        Book book;
        try {
            book = storeService.removeBookFromStore(storeId, bookId);
            BookReadOnlyDTO readOnlyDTO = Mapper.mapToReadOnlyDTO(book);
            return new ResponseEntity<>(readOnlyDTO,HttpStatus.OK);
        }catch (EntityNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Get a Store by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Store Found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserReadOnlyDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Store not found",
                    content = @Content)})
    @PreAuthorize("hasAuthority('PERSONAL') or hasAuthority('STORE')")
    @GetMapping("/{storeId}")
    public ResponseEntity<StoreReadOnlyDTO> getStore(@PathVariable("storeId") Long id) {
        Store store;
        try {
            store = storeService.getStoreById(id);
            StoreReadOnlyDTO dto = Mapper.mapToReadOnlyDTO(store);
            return ResponseEntity.ok(dto);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
