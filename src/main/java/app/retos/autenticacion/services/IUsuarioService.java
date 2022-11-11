package app.retos.autenticacion.services;

import app.retos.autenticacion.requests.UsersPw;

public interface IUsuarioService {

    public UsersPw findByUsername(String username);
}
