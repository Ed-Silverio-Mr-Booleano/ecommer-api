package ao.bilabila.ecommerce.api.core.domain.models;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}