package com.backend.lakik.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class RegisterRequest {
    @JsonProperty("username")
    private String username;

    @JsonProperty("password")
    private String password;

    @JsonProperty("email")
    private String email;

    @JsonProperty("role")
    private String role;

    @JsonProperty("namaKos")
    private String namaKos;

    @JsonProperty("nomorTeleponKos")
    private String nomorTeleponKos;

    @JsonProperty("alamatKos")
    private String alamatKos;

    @JsonProperty("deskripsiKos")
    private String deskripsiKos;
}
