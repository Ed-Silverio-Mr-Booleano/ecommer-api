package ao.bilabila.ecommerce.api.ports.in;

import ao.bilabila.ecommerce.api.core.domain.models.LoginResponse;
import ao.bilabila.ecommerce.api.core.domain.models.Usuario;

public interface IUsuarioUseCasePort {
    Long registerUsuario(Usuario usuario) throws Exception;
    LoginResponse loginUsuario(String username, String password) throws Exception;
}