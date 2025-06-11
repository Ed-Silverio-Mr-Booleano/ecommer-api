package ao.bilabila.ecommerce.api.core.domain.models;

import lombok.Data;

@Data
public class Produto {
    private Long id;
    private String nome;
    private Double preco;
    private Long categoriaId;
    private Integer estoque;
}