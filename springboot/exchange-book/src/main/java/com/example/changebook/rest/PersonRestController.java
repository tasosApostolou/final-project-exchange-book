package com.example.changebook.rest;

import com.example.changebook.dto.BookDTO.BookReadOnlyDTO;
import com.example.changebook.dto.personDTO.PersonReadonlyDTO;
import com.example.changebook.dto.personDTO.PersonUpdateDTO;
import com.example.changebook.dto.personDTO.RegisterPersonDTO;
import com.example.changebook.dto.usersDTO.UserReadOnlyDTO;
import com.example.changebook.mapper.Mapper;
import com.example.changebook.model.Book;
import com.example.changebook.model.Person;
import com.example.changebook.service.IPersonService;
import com.example.changebook.service.exceptions.EntityAlreadyExistsException;
import com.example.changebook.service.exceptions.EntityNotFoundException;
import com.example.changebook.validator.PersonUpdateValidator;
import com.example.changebook.validator.RegisterPersonValidator;
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
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/personal")
@Slf4j
public class PersonRestController {
    private final IPersonService personService;
    private final PersonUpdateValidator updateValidator;

    @Operation(summary = "Get persons by their lastname starting with initials")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Users Found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserReadOnlyDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid username supplied, Bad-Request",
                    content = @Content)})
    @GetMapping("/persons")
    public ResponseEntity<List<PersonReadonlyDTO>> getPersonsByLastnameStarting(@RequestParam("lastname") String lastname) {
        List<Person> persons;
        List<PersonReadonlyDTO> readOnlyDTOS = new ArrayList<>();
        try {
            persons = personService.getPersonsByLastname(lastname);

            for (Person person : persons) {
                readOnlyDTOS.add(Mapper.mapToReadOnlyDTO(person));
            }
            return new ResponseEntity<>(readOnlyDTOS, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//            throw e;
        }
    }


    @Operation(summary = "find persons who have a book with given title exactly")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Persons Found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = PersonReadonlyDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Persons not found",
                    content = @Content)})
    @GetMapping("/book/{title}")
    public ResponseEntity<List<PersonReadonlyDTO>> getPersonsByBookTitle(@PathVariable("title") String title) {
        List<Person> persons;
        List<PersonReadonlyDTO> readOnlyDTOS = new ArrayList<>();
        try {
            persons = personService.getPersonsByBookTitle(title);

            for (Person person : persons) {
                readOnlyDTOS.add(Mapper.mapToReadOnlyDTO(person));
            }
            return new ResponseEntity<>(readOnlyDTOS, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//            throw e;
        }
    }

    @Operation(summary = "Get a Person by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Person Found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = PersonReadonlyDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Person not found",
                    content = @Content)})
    @GetMapping("/{personId}")
    public ResponseEntity<PersonReadonlyDTO> getPerson(@PathVariable("personId") Long id) {
        Person person;
        try {
            person = personService.getPersonById(id);
            PersonReadonlyDTO dto = Mapper.mapToReadOnlyDTO(person);
            return ResponseEntity.ok(dto);

        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Update a Person")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Person updated",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PersonReadonlyDTO.class)) }),
            @ApiResponse(responseCode = "401", description = "Unauthorized user",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid input was supplied",
                    content = @Content)})
    @PutMapping("/update/{id}")
    public ResponseEntity<PersonReadonlyDTO> updatePerson(@PathVariable("id") Long id, @RequestBody @Schema(implementation = PersonUpdateDTO.class) PersonUpdateDTO dto, BindingResult bindingResult) {
        if(!(dto.getId() ==id)){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        updateValidator.validate(dto, bindingResult);
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            Person person = personService.updatePerson(dto);
            PersonReadonlyDTO readOnlyDTO = Mapper.mapToReadOnlyDTO(person);
            return new ResponseEntity<>(readOnlyDTO,HttpStatus.OK);
        }catch (EntityNotFoundException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    @Operation(summary = "Delete a Person by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Person Deleted",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PersonReadonlyDTO.class)) }),
            @ApiResponse(responseCode = "404", description = "Person not found",
                    content = @Content)})
    @DeleteMapping("/{id}")
    public ResponseEntity<PersonReadonlyDTO> deletePerson(@PathVariable("id") Long id){
        Person person;
        try {
            person = personService.deletePerson(id);
            PersonReadonlyDTO readOnlyDTO = Mapper.mapToReadOnlyDTO(person);
            return new ResponseEntity<>(readOnlyDTO,HttpStatus.OK);
        }catch (EntityNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "get all books of a person by person id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "books founs",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = PersonReadonlyDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Person not found or user not have added books yet",
                    content = @Content)})
    @GetMapping("{personId}/books")
    public ResponseEntity<List<BookReadOnlyDTO>> getAllBooksByPersonId(@PathVariable Long personId){
        List<Book> books;
//        System.out.println(userId);
        List<BookReadOnlyDTO> readOnlyDTOs = new ArrayList<>();

        try {
            books = personService.getAllBooksByPersonId(personId);

            for (Book book : books) {
                readOnlyDTOs.add(Mapper.mapToReadOnlyDTO(book));
            }
//if (readOnlyDTOs.isEmpty()) return new ResponseEntity<>( HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(readOnlyDTOs, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>( HttpStatus.NOT_FOUND);
//            throw e;
        }

    }


    @Operation(summary = "remove a book from person list with books(remove the book_id and person_id from middleware person_books)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "the book deleted from person list with books",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = PersonReadonlyDTO.class))}),
            @ApiResponse(responseCode = "404", description = "entity not found(person or book) searching by id in variable path ",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "personn does not have this book to delete, Unauthorized",
                    content = @Content)})
    @DeleteMapping("/{personId}/books/{bookId}")
    public ResponseEntity<BookReadOnlyDTO> removeBookFromPerson(@PathVariable Long personId, @PathVariable Long bookId) {
        Book book;
        try {
            book = personService.removeBookFromPerson(personId, bookId);
            BookReadOnlyDTO readOnlyDTO = Mapper.mapToReadOnlyDTO(book);
            return new ResponseEntity<>(readOnlyDTO,HttpStatus.OK);
        }catch (EntityNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("user/{userId}")
    public ResponseEntity<PersonReadonlyDTO> getPersonByUserId(@PathVariable("userId") Long userId){
       Person person;
       try{
           person = personService.getPersonByUserId(userId);
           PersonReadonlyDTO readOnlyDTO = Mapper.mapToReadOnlyDTO(person);
           return new ResponseEntity<>(readOnlyDTO,HttpStatus.OK);
       }catch (EntityNotFoundException e){
           return new ResponseEntity<>(HttpStatus.NOT_FOUND);
       }
    }

}



