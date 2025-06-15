package ao.bilabila.ecommerce.api.core.usecases;

import ao.bilabila.ecommerce.api.core.domain.models.Transacao;
import ao.bilabila.ecommerce.api.core.domain.models.Venda;
import ao.bilabila.ecommerce.api.core.domain.models.VendaProduto;
import ao.bilabila.ecommerce.api.core.domain.models.VendaResponse;
import ao.bilabila.ecommerce.api.ports.in.IProdutoUseCasePort;
import ao.bilabila.ecommerce.api.ports.in.IVendaUseCasePort;
import ao.bilabila.ecommerce.api.ports.out.IVendaRepositoryPort;
import ao.bilabila.ecommerce.api.ports.out.ITransacaoRepositoryPort;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

public class VendaUseCase implements IVendaUseCasePort {

    private final IVendaRepositoryPort vendaRepositoryPort;
    private final IProdutoUseCasePort produtoUseCase;
    private final ITransacaoRepositoryPort transacaoRepositoryPort;

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
            if (vendaProdutos == null || vendaProdutos.isEmpty()) {
                throw new Exception("Lista de produtos da venda é obrigatória");
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
    public List<VendaResponse> listarVendas() throws Exception {
        try {
            List<Venda> vendas = vendaRepositoryPort.findAllVendas();
            return vendas.stream()
                    .map(venda -> vendaRepositoryPort.convertToVendaResponse(venda)) // Explicitando o parâmetro
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public List<VendaResponse> listarVendasPorUser(Long clienteId) throws Exception {
        try {
            if (clienteId == null) {
                throw new Exception("Cliente ID é obrigatório");
            }
            List<Venda> vendas = vendaRepositoryPort.findVendasByClienteId(clienteId);
            return vendas.stream()
                    .map(venda -> vendaRepositoryPort.convertToVendaResponse(venda)) // Explicitando o parâmetro
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public VendaResponse listarVendaPorId(Long vendaId) throws Exception {
        try {
            if (vendaId == null) {
                throw new Exception("Venda ID é obrigatório");
            }
            Venda venda = vendaRepositoryPort.findVendaById(vendaId);
            return venda != null ? vendaRepositoryPort.convertToVendaResponse(venda) : null; // Explicitando o parâmetro
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public void mudarStatusOrder(Long vendaId, String estado) throws Exception {
        try {
            System.out.println("Estado: "+   estado);
            if (!List.of("progress", "ready", "shipping", "completed").contains(estado)) {
                throw new Exception("Estado inválido");
            }
            vendaRepositoryPort.updateVendaStatus(vendaId, estado);
        } catch (Exception e) {
            throw e;
        }
    }
}