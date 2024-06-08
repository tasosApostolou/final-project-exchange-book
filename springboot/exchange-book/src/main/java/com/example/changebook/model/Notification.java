package com.example.changebook.model;

import com.example.changebook.model.Identity.AbstractEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;


//@NoArgsConstructor










@AllArgsConstructor
@Setter
@Getter
@DynamicInsert
@Entity
@Table(name = "Notifications")
@EntityListeners(AuditingEntityListener.class)
//@ToString
public class Notification  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @ManyToOne
    @JoinColumn(name = "interesetdUser_id", referencedColumnName = "id")
    private User interestedUser;

//    private long interestedUserId;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User holderUSer;  // holder

    @ManyToOne
    @JoinColumn(name = "book_id", referencedColumnName = "id")
    private Book book;  // The book of interest

    @Column
    @Enumerated(EnumType.STRING)
    private NotificationType type;

    @Column
    private boolean isSeen;

    public Notification() {
    }
    public Notification(Long id,User interestedUser,User holderUSer, Book book, boolean isSeen) {
        this.id=id;
        this.interestedUser = interestedUser;
        this.holderUSer = holderUSer;
        this.book = book;
        this.isSeen = isSeen;
    }
    public boolean getIsSeen(){
        return this.isSeen;
    }

    public void setId(Long id) {
        this.id = id;
    }

}

