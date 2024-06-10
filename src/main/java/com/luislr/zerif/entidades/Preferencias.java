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

  @ManyToOne
  @JoinColumn(name = "id_idioma", referencedColumnName = "id_idioma", nullable = false)
  private Idioma idioma;

  @OneToOne
  @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario", nullable = false)
  private Usuario usuario;
}
