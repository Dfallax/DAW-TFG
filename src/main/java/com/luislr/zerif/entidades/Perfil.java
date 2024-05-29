package com.luislr.zerif.entidades;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@ToString
@Entity
@Table(name = "perfil")
public class Perfil {

  @Id
  @Column(name = "id_perfil")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true)
  private String nombre;

  @OneToMany(mappedBy = "perfil", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<Usuario> usuarios = new HashSet<>();

}
