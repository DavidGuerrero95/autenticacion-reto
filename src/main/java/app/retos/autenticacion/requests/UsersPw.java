package app.retos.autenticacion.requests;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@NoArgsConstructor
public class UsersPw {

    @NotNull(message = "Username no puede ser nulo")
    @Size(max = 20)
    @Indexed(unique = true)
    @JsonIgnore
    private String userId;

    @NotBlank(message = "Password no puede ser nulo")
    @Pattern(regexp = "[^ ]*+", message = "Caracter: ' ' (Espacio en blanco) invalido")
    @Size(min = 6, max = 20, message = "La contraseña debe tener entre 6 y 20 caracteres")
    private String password;

    private Boolean enabled;
    private Integer attempts;
    private Integer code;
    private List<Roles> roles;
    private Boolean firstSession;
    private String colour;


}
