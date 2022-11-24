package com.backend.lakik.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name= "kamar")
@JsonIgnoreProperties(value={ "user" }, allowSetters= true)
public class KamarModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NotNull
    @Column(name = "no_kamar")
    String noKamar;

    // Note: Implement Fasilitas as another model
    @Column(name = "wc_dalam")
    boolean wcDalam;

    boolean ac;

    @Column(name = "spring_bed")
    boolean springBed;

    boolean listrik;

    boolean tersedia;

    @Column(name = "keterangan", columnDefinition = "TEXT")
    String keterangan;

}
