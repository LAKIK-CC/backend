package com.backend.lakik.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Data
@Table(name= "users")
@JsonIgnoreProperties(value={ "password" }, allowSetters= true)
public class UserModel implements Serializable{
    @Id
    @NotNull
    @Column(name = "id_user")
    private String idUser;

    @NotNull
    @Size(max = 50)
    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Size(max = 50)
    @Column(name = "email", nullable = false)
    private String email;

    @NotNull
    @Column(name = "password", nullable = false, columnDefinition = "TEXT")
    private String password;

    @NotNull
    @Size(max = 50)
    @Column(name = "nama_kos", nullable = false, unique = true)
    private String namaKos;

    @NotNull
    @Size(max = 20)
    @Column(name = "nomor_telepon_kos", nullable = false)
    private String nomorTeleponKos;

    @NotNull
    @Column(name = "alamat_kos", nullable = false, columnDefinition = "TEXT")
    private String alamatKos;

    @NotNull
    @Column(name = "deskripsi_kos", nullable = true, columnDefinition = "TEXT")
    private String deskripsiKos;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_role", referencedColumnName = "idRole", nullable = false)
    @JsonIgnore
    private RoleModel role;

    @OneToMany
    private List<KamarModel> listKamar;
}
