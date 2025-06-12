package ao.bilabila.ecommerce.api.core.domain.models;

import lombok.Data;

@Data
public class VendaProduto {
    private Long vendaId;
    private Long produtoId;
    private Integer quantidadeComprada;
}