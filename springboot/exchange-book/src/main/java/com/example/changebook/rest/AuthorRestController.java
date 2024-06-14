package com.example.changebook.rest;

import com.example.changebook.dto.authorDTO.AuthorInsertDTO;
import com.example.changebook.dto.authorDTO.AuthorReadOnlyDTO;
import com.example.changebook.mapper.Mapper;
import com.example.changebook.model.Author;
import com.example.changebook.service.IAuthorService;
import com.example.changebook.service.exceptions.EntityNotFoundException;
import com.example.changebook.validator.AuthorInsertValidator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/author")
@RequiredArgsConstructor
public class AuthorRestController {
    private final IAuthorService authorService;
    private final AuthorInsertValidator authorInsertValidator;


    @Operation(summary = "Get authors by their name starting with initials")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Authors Found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AuthorReadOnlyDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid name supplied",
                    content = @Content)})
    @GetMapping("/authors")
    public ResponseEntity<List<AuthorReadOnlyDTO>> getAuthorByName(@RequestParam("name") String name) {
        List<Author> authors;
        System.out.println(name);
        List<AuthorReadOnlyDTO> readOnlyDTOs = new ArrayList<>();

        try {
            authors = authorService.getAuthorsByName(name);

            for (Author author : authors) {
                readOnlyDTOs.add(Mapper.mapToReadOnlyDTO(author));
            }
//if (readOnlyDTOs.isEmpty()) return new ResponseEntity<>( HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(readOnlyDTOs, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>( HttpStatus.BAD_REQUEST);
//            throw e;
        }
    }
    @Operation(summary = "Get a Author by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Author Found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AuthorReadOnlyDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Author not found",
                    content = @Content)})
        @GetMapping("/{authorID}")
        public ResponseEntity<AuthorReadOnlyDTO> getTeacher(@PathVariable("authorID") Long id) {
            Author author;

            try {
                author = authorService.getAuthorById(id);
                AuthorReadOnlyDTO dto = Mapper.mapToReadOnlyDTO(author);
                return ResponseEntity.ok(dto);

            } catch (EntityNotFoundException e) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }

    @Operation(summary = "Add a author")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Author created",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AuthorReadOnlyDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid input was supplied",
                    content = @Content),
            @ApiResponse(responseCode = "503", description = "Service Unavailable",
                    content = @Content)})
    @PostMapping("/authors")
    public ResponseEntity<AuthorReadOnlyDTO> addAuthor(@Valid @RequestBody AuthorInsertDTO dto, BindingResult bindingResult) {
        authorInsertValidator.validate(dto, bindingResult);
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        try {
            Author author = authorService.insert(dto);
            AuthorReadOnlyDTO authorReadOnlyDTO = Mapper.mapToReadOnlyDTO(author);
            URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(authorReadOnlyDTO.getId())
                    .toUri();
            return ResponseEntity.created(location).body(authorReadOnlyDTO);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
        }
    }
}

