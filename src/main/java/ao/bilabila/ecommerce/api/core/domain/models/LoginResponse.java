package ao.bilabila.ecommerce.api.core.domain.models;

import lombok.Data;

@Data
public class LoginResponse {
    private Long id;
    private String email;
    private String username;
    private String token;
    private  String role;
}