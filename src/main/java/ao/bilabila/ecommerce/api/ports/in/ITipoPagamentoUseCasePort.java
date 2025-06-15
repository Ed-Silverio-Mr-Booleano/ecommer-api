package ao.bilabila.ecommerce.api.ports.in;

import ao.bilabila.ecommerce.api.core.domain.models.TipoPagamento;

import java.util.List;

public interface ITipoPagamentoUseCasePort {
    List<TipoPagamento> listarTiposPagamento();
}