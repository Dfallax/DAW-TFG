package com.luislr.zerif.entidades;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@ToString
@Entity
@Table(name = "idiomas")
public class Idioma {
    @Id
    @Column(name = "id_idioma")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "lang", unique = true)
    private String lang;

    @Column(name = "idioma")
    private String idioma;
}
