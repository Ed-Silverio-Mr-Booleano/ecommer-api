package ao.bilabila.ecommerce.api.ports.in;

import ao.bilabila.ecommerce.api.core.domain.models.Transacao;

public interface ITransacaoUseCasePort {
    Long criarTransacao(Transacao transacao) throws Exception;
    void mudarStatusTransacao(Long transacaoId, String estado) throws Exception;
}