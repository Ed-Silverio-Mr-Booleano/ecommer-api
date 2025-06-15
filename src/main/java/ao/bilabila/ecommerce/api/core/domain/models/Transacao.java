package ao.bilabila.ecommerce.api.core.domain.models;

import lombok.Data;

@Data
public class Transacao {
    private Long id;
    private Long vendaId;
    private Long tipoPagamentoId;
    private String estado; // pending, completed, failed
}