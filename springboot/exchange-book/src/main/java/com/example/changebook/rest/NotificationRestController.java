package com.example.changebook.rest;

import com.example.changebook.dto.NotificationDTO.NotificationInsertDTO;
import com.example.changebook.dto.NotificationDTO.NotificationReadOnlyDTO;
import com.example.changebook.dto.NotificationDTO.NotificationUpdateDTO;
import com.example.changebook.mapper.Mapper;
import com.example.changebook.model.Notification;
import com.example.changebook.service.INotificationService;
import com.example.changebook.service.exceptions.EntityNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Objects;

@RestController
@RequestMapping("/api/notification")
@RequiredArgsConstructor
public class NotificationRestController {
   private final INotificationService notificationService;

    @Operation(summary = "Get Notification by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Notification Found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = NotificationReadOnlyDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Notification not found",
                    content = @Content)})
    @GetMapping("/{notificationID}")
    public ResponseEntity<NotificationReadOnlyDTO> getNotification (@PathVariable("notificationID") Long id){
        Notification notification;

        try {
            notification = notificationService.getNotificationById(id);
            NotificationReadOnlyDTO dto = Mapper.mapToReadOnlyDTO(notification);
            return ResponseEntity.ok(dto);

        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Add a notification")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Notification created",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = NotificationReadOnlyDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid input was supplied",
                    content = @Content),
            @ApiResponse(responseCode = "503", description = "Service Unavailable",
                    content = @Content)})
    @PostMapping("/add/{interestedID}")
    public ResponseEntity<NotificationReadOnlyDTO> createNotification(@PathVariable("interestedID")Long interestedID, @RequestBody NotificationInsertDTO dto, BindingResult bindingResult) {

        try {
            Notification notification = notificationService.insert(dto,interestedID);
            System.out.println(notification.getId()+"dssada");
            System.out.println(notification.getBook().getId());

            NotificationReadOnlyDTO readOnlyDTO = Mapper.mapToReadOnlyDTO(notification);
            System.out.println(notification.getHolderUSer().getId()+"============================================");
            URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(readOnlyDTO.getId())
                    .toUri();
            return ResponseEntity.created(location).body(readOnlyDTO);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    @Operation(summary = "Update a notification")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Teacher updated",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = NotificationReadOnlyDTO.class)) }),
            @ApiResponse(responseCode = "401", description = "Unauthorized user",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid input was supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Notification not found",
                    content = @Content) })
    @PutMapping("/notifications/{id}")
    public ResponseEntity<NotificationReadOnlyDTO> updateNotification(@PathVariable("id") Long id, @RequestBody NotificationUpdateDTO dto, BindingResult bindingResult) {
        if (!Objects.equals(id, dto.getId())) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        try {

            Notification notification = notificationService.update(dto);
            NotificationReadOnlyDTO readOnlyDTO = Mapper.mapToReadOnlyDTO(notification);
            return new ResponseEntity<>(readOnlyDTO,HttpStatus.OK);
        }catch (EntityNotFoundException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    @Operation(summary = "Delete a Notification by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Notification Deleted",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = NotificationReadOnlyDTO.class)) }),
            @ApiResponse(responseCode = "404", description = "Notification not found",
                    content = @Content)})
    @DeleteMapping("/{id}")
    public ResponseEntity<NotificationReadOnlyDTO> deleteNotification(@PathVariable("id") Long id){
        try {
            Notification notification = notificationService.delete(id);
            NotificationReadOnlyDTO readOnlyDTO = Mapper.mapToReadOnlyDTO(notification);
            return new ResponseEntity<>(readOnlyDTO,HttpStatus.OK);
        }catch (EntityNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }



}




