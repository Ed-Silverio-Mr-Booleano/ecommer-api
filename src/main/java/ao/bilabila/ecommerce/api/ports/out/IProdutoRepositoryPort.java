package ao.bilabila.ecommerce.api.ports.out;

import ao.bilabila.ecommerce.api.core.domain.models.Produto;
import java.util.List;

public interface IProdutoRepositoryPort {
    Produto save(Produto produto);
    List<Produto> findAll();
    Produto findById(Long id);
}