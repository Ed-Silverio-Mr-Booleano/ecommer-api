package ao.bilabila.ecommerce.api.core.usecases;

import ao.bilabila.ecommerce.api.core.domain.models.Venda;
import ao.bilabila.ecommerce.api.core.domain.models.VendaProduto;
import ao.bilabila.ecommerce.api.ports.in.IProdutoUseCasePort;
import ao.bilabila.ecommerce.api.ports.in.IVendaUseCasePort;
import ao.bilabila.ecommerce.api.ports.out.IVendaRepositoryPort;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class VendaUseCase implements IVendaUseCasePort {

    private final IVendaRepositoryPort vendaRepositoryPort;
    private final IProdutoUseCasePort produtoUseCase;

    public VendaUseCase(IVendaRepositoryPort vendaRepositoryPort, IProdutoUseCasePort produtoUseCase) {
        this.vendaRepositoryPort = vendaRepositoryPort;
        this.produtoUseCase = produtoUseCase;
    }

    @Override
    @Transactional
    public Long criarOrder(Venda venda, List<VendaProduto> vendaProdutos) throws Exception {
        try {
            if (venda == null || venda.getClienteId() == null) {
                throw new Exception("Venda inválida ou cliente ausente");
            }
            Long vendaId = vendaRepositoryPort.saveVenda(venda);
            for (VendaProduto vp : vendaProdutos) {
                if (vp.getQuantidadeComprada() == null || vp.getQuantidadeComprada() <= 0) {
                    throw new Exception("Quantidade inválida");
                }
                vp.setVendaId(vendaId); // Garante que o vendaId seja definido
                vendaRepositoryPort.saveVendaProduto(vp);
                produtoUseCase.updateStock(vp.getProdutoId(), vp.getQuantidadeComprada());
            }
            return vendaId;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public List<Venda> listarVendas() {
        try {
            return vendaRepositoryPort.findAllVendas();
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public void mudarStatusOrder(Long vendaId, String estado) throws Exception {
        try {
            if (!List.of("progress", "ready", "delivered", "completed").contains(estado)) {
                throw new Exception("Estado inválido");
            }
            vendaRepositoryPort.updateVendaStatus(vendaId, estado);
        } catch (Exception e) {
            throw e;
        }
    }
}