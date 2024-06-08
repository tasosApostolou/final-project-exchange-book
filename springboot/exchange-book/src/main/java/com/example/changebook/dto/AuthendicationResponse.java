package com.example.changebook.dto;


import lombok.Getter;

@Getter
public class AuthendicationResponse {
    private final String jwt;
    public AuthendicationResponse(String jwt) {
        this.jwt = jwt;
    }

}


