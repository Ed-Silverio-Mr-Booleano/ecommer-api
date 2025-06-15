package ao.bilabila.ecommerce.api.core.usecases;

import ao.bilabila.ecommerce.api.core.domain.models.TipoPagamento;
import ao.bilabila.ecommerce.api.ports.in.ITipoPagamentoUseCasePort;
import ao.bilabila.ecommerce.api.ports.out.ITipoPagamentoRepositoryPort;

import java.util.List;

public class TipoPagamentoUseCase implements ITipoPagamentoUseCasePort {

    private final ITipoPagamentoRepositoryPort tipoPagamentoRepositoryPort;

    public TipoPagamentoUseCase(ITipoPagamentoRepositoryPort tipoPagamentoRepositoryPort) {
        this.tipoPagamentoRepositoryPort = tipoPagamentoRepositoryPort;
    }

    @Override
    public List<TipoPagamento> listarTiposPagamento() {
        try {
            return tipoPagamentoRepositoryPort.findAll();
        } catch (Exception e) {
            throw e;
        }
    }
}