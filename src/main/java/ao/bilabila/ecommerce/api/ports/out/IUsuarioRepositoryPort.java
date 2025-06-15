package ao.bilabila.ecommerce.api.ports.out;

import ao.bilabila.ecommerce.api.core.domain.models.Usuario;

public interface IUsuarioRepositoryPort {
    Long saveUsuario(Usuario usuario);
    Usuario findByUsername(String username);
    Usuario findByEmail(String email);
}