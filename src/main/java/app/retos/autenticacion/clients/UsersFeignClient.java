package app.retos.autenticacion.clients;

import app.retos.autenticacion.requests.UsersPw;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "usuarios-reto")
public interface UsersFeignClient {

    @GetMapping("/usuarios/login/{username}")
	UsersPw autenticacion(@PathVariable("username") String username);

}
