package ao.bilabila.ecommerce.api.adapters.http;

import ao.bilabila.ecommerce.api.core.domain.models.LoginRequest;
import ao.bilabila.ecommerce.api.core.domain.models.LoginResponse;
import ao.bilabila.ecommerce.api.core.domain.models.Usuario;
import ao.bilabila.ecommerce.api.core.usecases.UsuarioUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UsuarioController {

    private final UsuarioUseCase usuarioUseCase;

    public UsuarioController(UsuarioUseCase usuarioUseCase) {
        this.usuarioUseCase = usuarioUseCase;
    }

    @PostMapping("/register")
    public ResponseEntity<Long> register(@RequestBody Usuario usuario) {
        try {
            Long userId = usuarioUseCase.registerUsuario(usuario);
            return ResponseEntity.ok(userId);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        try {
            LoginResponse response = usuarioUseCase.loginUsuario(loginRequest.getUsername(), loginRequest.getPassword());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}