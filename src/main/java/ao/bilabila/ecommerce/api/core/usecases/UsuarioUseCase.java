package ao.bilabila.ecommerce.api.core.usecases;

import ao.bilabila.ecommerce.api.core.domain.models.LoginResponse;
import ao.bilabila.ecommerce.api.core.domain.models.Usuario;
import ao.bilabila.ecommerce.api.ports.in.IUsuarioUseCasePort;
import ao.bilabila.ecommerce.api.ports.out.IUsuarioRepositoryPort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioUseCase implements IUsuarioUseCasePort {

    private final IUsuarioRepositoryPort usuarioRepositoryPort;
    private final BCryptPasswordEncoder passwordEncoder;

    public UsuarioUseCase(IUsuarioRepositoryPort usuarioRepositoryPort) {
        this.usuarioRepositoryPort = usuarioRepositoryPort;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public Long registerUsuario(Usuario usuario) throws Exception {
        if (usuario.getUsername() == null || usuario.getPassword() == null || usuario.getEmail() == null) {
            throw new Exception("Username, password e email são obrigatórios");
        }
        if (usuarioRepositoryPort.findByUsername(usuario.getUsername()) != null) {
            throw new Exception("Username já existe");
        }
        if (usuarioRepositoryPort.findByEmail(usuario.getEmail()) != null) {
            throw new Exception("Email já registrado");
        }
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        usuario.setCreatedAt(new java.sql.Timestamp(System.currentTimeMillis()));
        return usuarioRepositoryPort.saveUsuario(usuario);
    }

    @Override
    public LoginResponse loginUsuario(String username, String password) throws Exception {
        Usuario usuario = usuarioRepositoryPort.findByUsername(username);
        if (usuario == null || !passwordEncoder.matches(password, usuario.getPassword())) {
            throw new Exception("Credenciais inválidas");
        }
        // Gerar um token simples (substitua por JWT em produção)
        String token = "token-" + username + "-" + System.currentTimeMillis();

        // Montar a resposta
        LoginResponse response = new LoginResponse();
        response.setId(usuario.getId());
        response.setEmail(usuario.getEmail());
        response.setUsername(usuario.getUsername());
        response.setToken(token);
        response.setRole(usuario.getRole());
        return response;
    }
}