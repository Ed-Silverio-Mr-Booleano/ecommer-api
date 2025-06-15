package ao.bilabila.ecommerce.api.ports.out;

import ao.bilabila.ecommerce.api.core.domain.models.TipoPagamento;

import java.util.List;

public interface ITipoPagamentoRepositoryPort {
    List<TipoPagamento> findAll();
}