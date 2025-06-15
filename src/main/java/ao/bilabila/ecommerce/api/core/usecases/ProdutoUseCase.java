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
                throw new Exception("Nome do produto é obrigatório");
            }
            if (produto.getPreco() == null || produto.getPreco() < 0) {
                throw new Exception("Preço inválido");
            }
            if (produto.getEstoque() == null || produto.getEstoque() < 0) {
                throw new Exception("Estoque inválido");
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
    public Produto findProdutoById(Long id) throws Exception {
        try {
            return produtoRepositoryPort.findById(id);
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public void updateStock(Long produtoId, Integer quantidadeComprada) throws Exception {
        try {
            if (quantidadeComprada == null || quantidadeComprada <= 0) {
                throw new Exception("Quantidade comprada inválida");
            }
            produtoRepositoryPort.updateStock(produtoId, quantidadeComprada);
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public void deactivateProduto(Long id, String ative) throws Exception {
        try {
            produtoRepositoryPort.deactivateProduto(id, ative);
        } catch (Exception e) {
            throw e;
        }
    }
}