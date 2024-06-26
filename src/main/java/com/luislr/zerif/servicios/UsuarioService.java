package com.luislr.zerif.servicios;

import com.luislr.zerif.dto.usuario.UsuarioMapper;
import com.luislr.zerif.dto.usuario.UsuarioSignupDto;
import com.luislr.zerif.entidades.Usuario;
import com.luislr.zerif.repositorios.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UsuarioService {

	private final UsuarioRepository usuarioRepository;

	private final PasswordEncoder passwordEncoder;
	private final UsuarioMapper mapper;


	public Usuario save(Usuario u) {
		u.setPassword(passwordEncoder.encode(u.getPassword()));
		return usuarioRepository.save(u);
	}

	public Usuario save(UsuarioSignupDto dto) {
		Usuario usuario = mapper.toEntity(dto);
		usuario.setPassword(passwordEncoder.encode(dto.password()));
		return usuarioRepository.save(usuario);
	}

	public List<Usuario> saveAll (List<Usuario> lista) {
		lista.forEach(usuario ->
						usuario.setPassword(passwordEncoder.encode(usuario.getPassword())));
		return usuarioRepository.saveAll(lista); }

	public Usuario findById(long id) {
		return usuarioRepository.findById(id).orElse(null);
	}

	public Usuario findByUsername(String username) {
		return usuarioRepository.findByUsername(username).orElse(null);
	}

	public Usuario buscarPorUsernameOEmail(String s) {
		return usuarioRepository.buscarPorUsernameOEmail(s).orElse(null);
	}

	public Usuario findByUsernameOrEmail(String username, String email) {
		return usuarioRepository.findByUsernameOrEmail(username,email).orElse(null);
	}

	public List<Usuario> findAll(){
		return usuarioRepository.findAll();
	}

}
