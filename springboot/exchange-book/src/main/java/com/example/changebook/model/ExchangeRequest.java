//package com.example.changebook.model;
//
//import com.example.changebook.model.Identity.AbstractEntity;
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//import java.time.LocalDateTime;
//
//@Entity
//@Table(name = "exchange_requests")
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//public class ExchangeRequest extends AbstractEntity {
////    @Id
////    @GeneratedValue(strategy = GenerationType.IDENTITY)
////    private Long id;
//
//    @ManyToOne
//    @JoinColumn(name = "requester_id", referencedColumnName = "id")
//    private User requester;
//
//    @ManyToOne
//    @JoinColumn(name = "responder_id", referencedColumnName = "id")
//    private User responder;
//
//    @ManyToOne
//    @JoinColumn(name = "requested_book_id", referencedColumnName = "id")
//    private Book requestedBook;
//
//    @ManyToOne
//    @JoinColumn(name = "offered_book_id", referencedColumnName = "id")
//    private Book offeredBook;
//
//    @Column
//    private LocalDateTime timestamp;
//
//    @Column
//    private String status; // e.g., "pending", "accepted", "declined"
//
//    // Getters and setters
//}