package ao.bilabila.ecommerce.api.ports.in;

import ao.bilabila.ecommerce.api.core.domain.models.Transacao;
import ao.bilabila.ecommerce.api.core.domain.models.Venda;
import ao.bilabila.ecommerce.api.core.domain.models.VendaResponse;
import ao.bilabila.ecommerce.api.core.domain.models.VendaProduto;

import java.util.List;

public interface IVendaUseCasePort {
    Long criarOrder(Venda venda, List<VendaProduto> vendaProdutos, Transacao transacao) throws Exception;
    List<VendaResponse> listarVendas() throws Exception;
    List<VendaResponse> listarVendasPorUser(Long clienteId) throws Exception;
    VendaResponse listarVendaPorId(Long vendaId) throws Exception;
    void mudarStatusOrder(Long vendaId, String estado) throws Exception;
}