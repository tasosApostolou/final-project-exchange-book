package com.example.changebook.rest;

import com.example.changebook.dto.StoreDTO.StoreReadOnlyDTO;
import com.example.changebook.dto.StoreDTO.StoreRegisterDTO;
import com.example.changebook.dto.personDTO.PersonReadonlyDTO;
import com.example.changebook.dto.personDTO.RegisterPersonDTO;
import com.example.changebook.dto.usersDTO.UserReadOnlyDTO;
import com.example.changebook.mapper.Mapper;
import com.example.changebook.model.Person;
import com.example.changebook.model.Store;
import com.example.changebook.service.IPersonService;
import com.example.changebook.service.IStoreService;
import com.example.changebook.service.exceptions.EntityAlreadyExistsException;
import com.example.changebook.validator.PersonUpdateValidator;
import com.example.changebook.validator.RegisterPersonValidator;
import com.example.changebook.validator.StoreRegisterValidator;
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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@AllArgsConstructor
@RequestMapping("/api/register")
@Slf4j
public class RegisterRestController {
    private final IPersonService personService;
    private final IStoreService storeService;
    private final RegisterPersonValidator registerPersonValidator;
    private final StoreRegisterValidator storeRegisterValidator;


    @Operation(summary = "register a person")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Person created",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = PersonReadonlyDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid input was supplied",
                    content = @Content),
            @ApiResponse(responseCode = "503", description = "Service Unavailable",
                    content = @Content)})
    @PostMapping("/Person")
    public ResponseEntity<PersonReadonlyDTO> RegisterPerson(@Valid @RequestBody @Schema(implementation = RegisterPersonDTO.class) RegisterPersonDTO dto, BindingResult bindingResult) throws EntityAlreadyExistsException {
        registerPersonValidator.validate(dto,bindingResult);
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Person createdPerson;
        try {
            createdPerson = personService.registerPerson(dto);
            PersonReadonlyDTO personReadonlyDTO = Mapper.mapToReadOnlyDTO(createdPerson);
            URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(personReadonlyDTO.getId())
                    .toUri();
            return ResponseEntity.created(location).body(personReadonlyDTO);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
        }
    }
    @Operation(summary = "register a Store")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Store created",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserReadOnlyDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid input was supplied",
                    content = @Content),
            @ApiResponse(responseCode = "503", description = "Service Unavailable",
                    content = @Content)})
    @PostMapping("/store")
    public ResponseEntity<StoreReadOnlyDTO> RegisterStore(@Valid @RequestBody @Schema(implementation = StoreRegisterDTO.class) StoreRegisterDTO dto, BindingResult bindingResult) throws EntityAlreadyExistsException {
        storeRegisterValidator.validate(dto,bindingResult);
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Store createdStore;
        try {
            createdStore = storeService.registerStore(dto);
            StoreReadOnlyDTO storeReadOnlyDTO = Mapper.mapToReadOnlyDTO(createdStore);
            URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(storeReadOnlyDTO.getUserId())
                    .toUri();
            return ResponseEntity.created(location).body(storeReadOnlyDTO);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
        }
    }
}
