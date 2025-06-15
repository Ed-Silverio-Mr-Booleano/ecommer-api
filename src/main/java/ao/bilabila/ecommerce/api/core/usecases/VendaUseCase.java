package ao.bilabila.ecommerce.api.core.usecases;

import ao.bilabila.ecommerce.api.core.domain.models.Transacao;
import ao.bilabila.ecommerce.api.core.domain.models.Venda;
import ao.bilabila.ecommerce.api.core.domain.models.VendaProduto;
import ao.bilabila.ecommerce.api.ports.in.IProdutoUseCasePort;
import ao.bilabila.ecommerce.api.ports.in.IVendaUseCasePort;
import ao.bilabila.ecommerce.api.ports.out.ITransacaoRepositoryPort;
import ao.bilabila.ecommerce.api.ports.out.IVendaRepositoryPort;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class VendaUseCase implements IVendaUseCasePort {

    private final IVendaRepositoryPort vendaRepositoryPort;
    private final IProdutoUseCasePort produtoUseCase;
    private final ITransacaoRepositoryPort transacaoRepositoryPort; // Nova dependência

    public VendaUseCase(IVendaRepositoryPort vendaRepositoryPort, IProdutoUseCasePort produtoUseCase, ITransacaoRepositoryPort transacaoRepositoryPort) {
        this.vendaRepositoryPort = vendaRepositoryPort;
        this.produtoUseCase = produtoUseCase;
        this.transacaoRepositoryPort = transacaoRepositoryPort;
    }

    @Override
    @Transactional
    public Long criarOrder(Venda venda, List<VendaProduto> vendaProdutos, Transacao transacao) throws Exception {
        try {
            if (venda == null || venda.getClienteId() == null) {
                throw new Exception("Venda inválida ou cliente ausente");
            }
            Long vendaId = vendaRepositoryPort.saveVenda(venda);
            for (VendaProduto vp : vendaProdutos) {
                if (vp.getQuantidadeComprada() == null || vp.getQuantidadeComprada() <= 0) {
                    throw new Exception("Quantidade inválida");
                }
                vp.setVendaId(vendaId);
                vendaRepositoryPort.saveVendaProduto(vp);
                produtoUseCase.updateStock(vp.getProdutoId(), vp.getQuantidadeComprada());
            }
            // Criar transação
            if (transacao != null) {
                transacao.setVendaId(vendaId);
                if (transacao.getTipoPagamentoId() == null) {
                    throw new Exception("Tipo de pagamento ausente");
                }
                if (!List.of("pending", "completed", "failed").contains(transacao.getEstado())) {
                    throw new Exception("Estado de transação inválido");
                }
                transacaoRepositoryPort.saveTransacao(transacao);
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