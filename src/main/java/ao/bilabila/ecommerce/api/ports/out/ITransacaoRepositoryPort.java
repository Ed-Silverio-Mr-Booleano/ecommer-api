package ao.bilabila.ecommerce.api.ports.out;

import ao.bilabila.ecommerce.api.core.domain.models.Transacao;

public interface ITransacaoRepositoryPort {
    Long saveTransacao(Transacao transacao);
    void updateTransacaoStatus(Long transacaoId, String estado);
}