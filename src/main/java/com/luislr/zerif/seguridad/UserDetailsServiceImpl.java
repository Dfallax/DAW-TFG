package com.luislr.zerif.seguridad;

import com.luislr.zerif.entidades.Usuario;
import com.luislr.zerif.repositorios.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

	private final UsuarioRepository usuarioRepository;

	@Override
	public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
		Usuario usuario = usuarioRepository.buscarPorUsernameOEmail(s)
				.orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

		return User.builder()
				.username(usuario.getUsername())
				.password(usuario.getPassword())
				.authorities(Collections.singleton(new SimpleGrantedAuthority(usuario.getPerfil().getNombre())))
				.disabled(false)
				.build();

	}

}
