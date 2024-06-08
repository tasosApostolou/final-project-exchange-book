//package com.example.changebook.model;
//
//import jakarta.persistence.Column;
//import jakarta.persistence.Entity;
//import jakarta.persistence.OneToMany;
//import lombok.*;
//import com.example.changebook.model.Identity.AbstractEntity;
//
//import java.util.HashSet;
//import java.util.Set;
//
//@Entity
//@NoArgsConstructor
//@AllArgsConstructor
//@Getter
//@Setter
//public class Cities extends AbstractEntity {
//    @Column(length = 50)
//    private String city;
//
//    @OneToMany(mappedBy = "city")
//    @Getter(AccessLevel.PROTECTED)
//    private Set<Person> persons = new HashSet<>();

//    public void addPerson(Person person){
//        this.persons.add(person);
//        person.setCity(this);
////    }
//}
