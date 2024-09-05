package com.example.changebook.rest;


import com.example.changebook.dto.BookDTO.*;
import com.example.changebook.mapper.Mapper;
import com.example.changebook.model.Book;
import com.example.changebook.model.Store;
import com.example.changebook.model.StoreBook;
import com.example.changebook.model.User;
import com.example.changebook.service.IBookService;
import com.example.changebook.service.IStoreService;
import com.example.changebook.service.IUserService;
import com.example.changebook.service.exceptions.EntityNotFoundException;
import com.example.changebook.validator.BookInsertValidator;
import com.example.changebook.validator.StoreBookInsertValidator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@AllArgsConstructor
@RequestMapping("/api/book")
@Slf4j
public class BookRestController {
    private final IBookService bookService;
    private final IUserService userService;
    private final IStoreService storeService;
    private final BookInsertValidator bookInsertValidator;
    private final StoreBookInsertValidator storeBookInsertValidator;

    @Operation(summary = " Create and Add a book to user(in middleware person_books) also create author if is not exist else add a book into existed author Set<Book> books ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Book added",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = BookReadOnlyDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid input was supplied",
                    content = @Content),
            @ApiResponse(responseCode = "503", description = "Service Unavailable",
                    content = @Content)})
    @PreAuthorize("hasAuthority('PERSONAL')")
    @PostMapping("/personal/{personID}/add")
    public ResponseEntity<BookReadOnlyDTO> addBookToPerson(@PathVariable("personID") Long personID, @RequestBody @Valid BookInsertDTO bookDTO, Principal principal, BindingResult bindingResult) throws EntityNotFoundException {
        bookInsertValidator.validate(bookDTO,bindingResult);
        User authUser;
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Book book= new Book();
        try {
            authUser = userService.getUserByUsername(principal.getName());
            if(authUser.getPerson().getId() != personID){
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        }
            book = bookService.insertBookToPerson(personID,bookDTO);
            System.out.println("pre" + book.toString());
            log.info(book.getTitle() + book.getAuthor().getName());
            BookReadOnlyDTO bookReadOnlyDTO = Mapper.mapToReadOnlyDTO(book);
            URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(book.getId())
                    .toUri();
            return ResponseEntity.created(location).body(bookReadOnlyDTO);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    @Operation(summary = " Add or create and add a book to store(in middleware store_book) also create author if is not exist else add a book into existed author Set<Book> books ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Book added",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = BookReadOnlyDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid input was supplied",
                    content = @Content),
            @ApiResponse(responseCode = "503", description = "Service Unavailable",
                    content = @Content)})
    @PreAuthorize("hasAuthority('STORE')")
    @PostMapping("/store/{storeID}/add")
    public ResponseEntity<StoreBookReadOnlyDTO> addBookToStore(@PathVariable("storeID") Long storeID, @RequestBody @Valid StoreBookInsertDTO dto,Principal principal, BindingResult bindingResult) {
        storeBookInsertValidator.validate(dto,bindingResult);
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        StoreBook storeBook = new StoreBook();
        StoreBookReadOnlyDTO readOnlyDTO;
        User authUser;
        Store authStore;
        try {
            authUser = userService.getUserByUsername(principal.getName());
            authStore = authUser.getStore();
            if(!Objects.equals(storeID, authStore.getId())){
                return  new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
            storeBook = bookService.insertBookToStore(authStore,dto);
            readOnlyDTO = Mapper.mapToReadOnlyDTO(storeBook);
            URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(storeBook.getStore().getId())
                    .toUri();
            return ResponseEntity.created(location).body(readOnlyDTO);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    @Operation(summary = "Get books by their title starting with initials and the users list of each book ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Books Found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = BookReadWithPersonsSetDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid Title supplied",
                    content = @Content)})
    @GetMapping("/books/{title}")
    public ResponseEntity<List<BookReadWithPersonsSetDTO>> getBooksByTitle(@PathVariable("title") String title) {
        List<Book> books;
        System.out.println(title);
        List<BookReadWithPersonsSetDTO> readOnlyDTOs = new ArrayList<>();
        try {
            books = bookService.getBookByTitle(title);
            for (Book book : books) {
                //including the "set<User> users" of every book to a list with books which every book contains a list with users details. book_id is matched with all user_id's in user_books table
                readOnlyDTOs.add(Mapper.mapToReadOnlyDTOwithPersonDetails(book));
                System.out.println(books);
            }
            return new ResponseEntity<>(readOnlyDTOs, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>( HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Get books by their author.name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Books Found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = BookReadOnlyDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid author name or not exists supplied",
                    content = @Content)})
    @GetMapping("/author/{authorName}")
    public ResponseEntity<List<BookReadOnlyDTO>> findBooksByAuthor(@PathVariable("authorName") String name) {
        List<Book> books;
        List<BookReadOnlyDTO> readOnlyDTOs = new ArrayList<>();

        try {
            books = bookService.getBooksByAuthorName(name);

            for (Book book : books) {
                readOnlyDTOs.add(Mapper.mapToReadOnlyDTO(book));
            }
            return new ResponseEntity<>(readOnlyDTOs, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>( HttpStatus.BAD_REQUEST);
//            throw e;
        }
    }

    @Operation(summary = "Get a Book by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Book Found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = BookReadOnlyDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Book not found",
                    content = @Content)})
    @GetMapping("/{bookID}")
    public ResponseEntity<BookReadOnlyDTO> getBook(@PathVariable("bookID") Long id) {
        Book book;
        try {
            book = bookService.getBookById(id);
            BookReadOnlyDTO dto = Mapper.mapToReadOnlyDTO(book);
            return ResponseEntity.ok(dto);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    @Operation(summary = "Get the books of a user by person_id ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Books Found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = BookReadOnlyDTO.class))}),
            @ApiResponse(responseCode = "404", description = "user not found OR user has no books added yet",
                    content = @Content)})
    @GetMapping("/person/{personId}")
    public ResponseEntity<List<BookReadOnlyDTO>> getBooksByPersonID(@PathVariable("personId") Long personId) {
        List<Book> books;
        List<BookReadOnlyDTO> readOnlyDTOs = new ArrayList<>();
        try {
            books = bookService.getBookByPersonId(personId);

            for (Book book : books) {
                readOnlyDTOs.add(Mapper.mapToReadOnlyDTO(book));
            }
            return new ResponseEntity<>(readOnlyDTOs, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>( HttpStatus.NOT_FOUND);

        }
    }

    @PreAuthorize("hasAuthority('PERSONAL') or hasAuthority('STORE')")
    @GetMapping("/store")
    public ResponseEntity<List<StoreBookReadOnlyDTO>> getStoreBooksByBookTitle(@RequestParam("title") String title){
        List<StoreBook> books;
        List<StoreBookReadOnlyDTO> readOnlyDTOs = new ArrayList<>();

        try {
            books = storeService.getStoreBooksByBookTitle(title);
            for (StoreBook book : books) {
                readOnlyDTOs.add(Mapper.mapToReadOnlyDTO(book));
            }
            if (readOnlyDTOs.isEmpty()) return new ResponseEntity<>( HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(readOnlyDTOs, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>( HttpStatus.NOT_FOUND);
        }
    }
}
