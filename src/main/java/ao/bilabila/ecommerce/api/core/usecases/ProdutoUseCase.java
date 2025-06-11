package ao.bilabila.ecommerce.api.core.usecases;

import ao.bilabila.ecommerce.api.core.domain.models.Produto;
import ao.bilabila.ecommerce.api.ports.in.IProdutoUseCasePort;
import ao.bilabila.ecommerce.api.ports.out.IProdutoRepositoryPort;

import java.util.List;

public class ProdutoUseCase implements IProdutoUseCasePort {

    private final IProdutoRepositoryPort produtoRepositoryPort;

    public ProdutoUseCase(IProdutoRepositoryPort produtoRepositoryPort) {
        this.produtoRepositoryPort = produtoRepositoryPort;
    }

    @Override
    public Produto saveProduto(Produto produto) throws Exception {
        try {
            if (produto == null || produto.getNome() == null || produto.getNome().trim().isEmpty()) {
                throw new Exception("Erro ao salvar o produto");
            }
            return produtoRepositoryPort.save(produto);
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public List<Produto> findAllProdutos() throws Exception {
        try {
            List<Produto> produtos = produtoRepositoryPort.findAll();
            if (produtos == null || produtos.isEmpty()) {
                throw new Exception("Nenhum produto encontrado");
            }
            return produtos;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Produto findProdutoById(Long id) {
        try {
            return produtoRepositoryPort.findById(id);
        } catch (Exception e) {
            throw e;
        }
    }
}