package com.backend.lakik.rest.dto;

import lombok.Data;

@Data
public class RegisterRequest {
    private String username;
    private String password;
    private String namaLengkap;
    private String email;
    private String role;
}
