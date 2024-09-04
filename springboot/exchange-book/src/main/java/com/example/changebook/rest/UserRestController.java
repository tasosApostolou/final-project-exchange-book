package com.example.changebook.rest;

import com.example.changebook.dto.NotificationDTO.NotificationReadOnlyDTO;
import com.example.changebook.dto.usersDTO.UserInsertDTO;
import com.example.changebook.dto.usersDTO.UserReadOnlyDTO;
import com.example.changebook.dto.usersDTO.UserUpdateDTO;
import com.example.changebook.mapper.Mapper;
import com.example.changebook.model.Notification;
import com.example.changebook.model.User;
import com.example.changebook.service.IUserService;
import com.example.changebook.service.exceptions.EntityNotFoundException;
import com.example.changebook.validator.UserInsertValidator;
import com.example.changebook.validator.UserUpdateValidator;
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
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserRestController {

    private final IUserService userService;
    private final UserInsertValidator insertValidator;
    private final UserUpdateValidator updateValidator;
    @Operation(summary = "Get a User by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User Found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserReadOnlyDTO.class))}),
            @ApiResponse(responseCode = "400", description = "User not found",
                    content = @Content)})
    @GetMapping("/{userID}")
    public ResponseEntity<UserReadOnlyDTO> getUser(@PathVariable("userID") Long id) {
        User user;

        try {
            user = userService.getUserById(id);
            UserReadOnlyDTO dto = Mapper.mapToReadOnlyDTO(user);
            return ResponseEntity.ok(dto);

        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Add a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserReadOnlyDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid input was supplied",
                    content = @Content),
            @ApiResponse(responseCode = "503", description = "Service Unavailable",
                    content = @Content)})
    @PostMapping("/users")
    public ResponseEntity<UserReadOnlyDTO> addUser(@Valid @RequestBody @Schema(implementation = UserInsertDTO.class) UserInsertDTO dto, BindingResult bindingResult) {
        insertValidator.validate(dto, bindingResult);
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        try {
            User user = userService.insertUser(dto);
            UserReadOnlyDTO userReadOnlyDTO = Mapper.mapToReadOnlyDTO(user);
            URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(userReadOnlyDTO.getId())
                    .toUri();
            return ResponseEntity.created(location).body(userReadOnlyDTO);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    @Operation(summary = "Update a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User updated",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserReadOnlyDTO.class)) }),
            @ApiResponse(responseCode = "401", description = "Unauthorized user",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid input was supplied",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content) })

    @PutMapping("/update/{id}")
    public ResponseEntity<UserReadOnlyDTO> updateUser(@PathVariable("id") Long id, @Valid @RequestBody @Schema(implementation = UserUpdateDTO.class) UserUpdateDTO dto, Principal principal, BindingResult bindingResult) {
        updateValidator.validate(dto, bindingResult);
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        String authenticatedUsername = principal.getName();
        User authenticatedUser = userService.findByUsername(authenticatedUsername);
//        if (! authenticatedUsername.equals(dto.getUsername())){
//            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
//        }
        if (!Objects.equals(authenticatedUser.getId(), dto.getId())) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        try {

            User user = userService.updateUser(dto);
            UserReadOnlyDTO readOnlyDTO = Mapper.mapToReadOnlyDTO(user);
            return new ResponseEntity<>(readOnlyDTO,HttpStatus.OK);
        }catch (EntityNotFoundException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Delete a User by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User Deleted",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserReadOnlyDTO.class)) }),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content)})
    @DeleteMapping("/{id}")
    public ResponseEntity<UserReadOnlyDTO> deleteUser(@PathVariable("id") Long id){
        try {
            User user = userService.deleteUser(id);
            UserReadOnlyDTO readOnlyDTO = Mapper.mapToReadOnlyDTO(user);
            return new ResponseEntity<>(readOnlyDTO,HttpStatus.OK);
        }catch (EntityNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @Operation(summary = "get notifications by HolderUser id ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "the book deleted from user list with books",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserReadOnlyDTO.class))}),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content)})
    @GetMapping("/notification/{userId}")
    public ResponseEntity<List<NotificationReadOnlyDTO>> getNotificationsByUserID(@PathVariable("userId") Long userId) {
        List<Notification> notifications;
        List<NotificationReadOnlyDTO> readOnlyDTOs = new ArrayList<>();

        try {
            notifications = userService.getNotificationsByUserId(userId);
            for (Notification notification : notifications) {
                readOnlyDTOs.add(Mapper.mapToReadOnlyDTO(notification));
            }
            return new ResponseEntity<>(readOnlyDTOs, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>( HttpStatus.NOT_FOUND);
        }
    }

}



