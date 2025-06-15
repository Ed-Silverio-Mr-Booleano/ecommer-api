package ao.bilabila.ecommerce.api.ports.out;

import ao.bilabila.ecommerce.api.core.domain.models.Venda;
import ao.bilabila.ecommerce.api.core.domain.models.VendaProduto;
import ao.bilabila.ecommerce.api.core.domain.models.VendaResponse;

import java.util.List;

public interface IVendaRepositoryPort {
    Long saveVenda(Venda venda);
    void saveVendaProduto(VendaProduto vendaProduto);
    void updateVendaStatus(Long vendaId, String estado);
    List<Venda> findAllVendas();
    List<Venda> findVendasByClienteId(Long clienteId);
    Venda findVendaById(Long vendaId);
    VendaResponse convertToVendaResponse(Venda venda); // Novo método
}