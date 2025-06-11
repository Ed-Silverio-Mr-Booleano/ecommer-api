package ao.bilabila.ecommerce.api.ports.in;

import ao.bilabila.ecommerce.api.core.domain.models.Produto;
import java.util.List;

public interface IProdutoUseCasePort {
    Produto saveProduto(Produto produto) throws Exception;
    List<Produto> findAllProdutos() throws Exception;
    Produto findProdutoById(Long id) throws Exception;
}