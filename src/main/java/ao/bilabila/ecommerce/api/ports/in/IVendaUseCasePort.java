package ao.bilabila.ecommerce.api.ports.in;

import ao.bilabila.ecommerce.api.core.domain.models.Venda;
import ao.bilabila.ecommerce.api.core.domain.models.VendaProduto;
import java.util.List;

public interface IVendaUseCasePort {

    Long criarOrder(Venda venda, List<VendaProduto> vendaProdutos) throws Exception;

    List<Venda> listarVendas() throws Exception;

    void mudarStatusOrder(Long vendaId, String estado) throws Exception;
}