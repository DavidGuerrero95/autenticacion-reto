package app.retos.autenticacion.controllers;

import app.retos.autenticacion.clients.UsersFeignClient;
import app.retos.autenticacion.requests.UsersPw;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@Slf4j
public class AutenticacionController {

    @Autowired
    UsersFeignClient uClient;

    @CircuitBreaker(name = "usuarios", fallbackMethod = "iniciarSesionAlternativo")
    public UsersPw obtenerUsuario(String username) {
        return uClient.autenticacion(username);
    }

    @SuppressWarnings("unused")
    private UsersPw iniciarSesionAlternativo(String username, Throwable e) {
        log.info(e.getMessage());
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Servicio Usuarios no disponible");
    }

}
