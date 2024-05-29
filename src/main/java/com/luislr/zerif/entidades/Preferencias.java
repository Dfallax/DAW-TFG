package com.luislr.zerif.entidades;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@ToString
@Entity
@Table(name = "preferencias")
public class Preferencias {
  @Id
  @Column(name = "id_preferencias")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String idioma;

  @OneToOne
  @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario", nullable = false)
  private Usuario usuario;
}
