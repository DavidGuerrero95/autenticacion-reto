package app.retos.autenticacion.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;

import app.retos.autenticacion.requests.UsersPw;

public interface UsuarioPwRepository extends MongoRepository<UsersPw, String> {

	@RestResource(path = "buscar")
	public UsersPw findByUsername(@Param("username") String username);
	
	@RestResource(path = "exist-user")
	public Boolean existsByUsername(@Param("username") String username);

}