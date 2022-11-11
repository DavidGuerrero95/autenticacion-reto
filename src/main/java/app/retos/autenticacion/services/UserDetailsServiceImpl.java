package app.retos.autenticacion.services;

import java.util.List;
import java.util.stream.Collectors;

import app.retos.autenticacion.controllers.AutenticacionController;
import app.retos.autenticacion.requests.UsersPw;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;



@Service
public class UserDetailsServiceImpl implements UserDetailsService, IUsuarioService {

	private Logger log = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

	@Autowired
	AutenticacionController autenticacion;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		UsersPw usuario = autenticacion.obtenerUsuario(username);

		if (usuario == null) {
			String error = "Error en el login, no existe el usuario '" + username + "'en el sistema";
			log.error(error);
			throw new UsernameNotFoundException(error);
		}

		List<GrantedAuthority> authorities = usuario.getRoles().stream()
				.map(role -> new SimpleGrantedAuthority(role.getName()))
				.peek(authority -> log.info("Role: " + authority.getAuthority())).collect(Collectors.toList());


		log.info("Usuario autenticado: " + username);
		return new User(username, usuario.getPassword(), usuario.getEnabled(), true, true, true,
				authorities);

	}

	@Override
	public UsersPw findByUsername(String username) {
		return autenticacion.obtenerUsuario(username);
	}
}

