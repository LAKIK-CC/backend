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

    @JsonProperty("nama_kos")
    private String namaKos;

    @JsonProperty("nomor_telepon_kos")
    private String nomorTeleponKos;

    @JsonProperty("alamat_kos")
    private String alamatKos;

    @JsonProperty("deskripsi_kos")
    private String deskripsiKos;
}
