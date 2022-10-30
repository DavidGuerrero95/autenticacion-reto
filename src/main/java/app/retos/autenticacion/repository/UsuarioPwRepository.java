package app.retos.autenticacion.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;

import app.retos.autenticacion.requests.UsersPw;

public interface UsuarioPwRepository extends MongoRepository<UsersPw, String> {

	@RestResource(path = "buscar-userId")
	public UsersPw findByUserId(@Param("userId") String userId);
	
	@RestResource(path = "exist-userId")
	public Boolean existsByUserId(@Param("userId") String userId);

}