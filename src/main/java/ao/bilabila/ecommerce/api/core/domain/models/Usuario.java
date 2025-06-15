package ao.bilabila.ecommerce.api.core.domain.models;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class Usuario {
    private Long id;
    private String username;
    private String password; // Deve ser criptografado (ex.: com BCrypt)
    private String email;
    private Timestamp createdAt;
    private String role; // Ex.: "USER", "ADMIN"
}