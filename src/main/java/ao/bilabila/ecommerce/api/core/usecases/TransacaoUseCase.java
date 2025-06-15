package ao.bilabila.ecommerce.api.core.usecases;

import ao.bilabila.ecommerce.api.core.domain.models.Transacao;
import ao.bilabila.ecommerce.api.ports.in.ITipoPagamentoUseCasePort;
import ao.bilabila.ecommerce.api.ports.in.ITransacaoUseCasePort;
import ao.bilabila.ecommerce.api.ports.out.ITransacaoRepositoryPort;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class TransacaoUseCase implements ITransacaoUseCasePort {

    private final ITransacaoRepositoryPort transacaoRepositoryPort;

    public TransacaoUseCase(ITransacaoRepositoryPort transacaoRepositoryPort, ITipoPagamentoUseCasePort tipoPagamentoUseCasePort) {
        this.transacaoRepositoryPort = transacaoRepositoryPort;
    }

    @Override
    @Transactional
    public Long criarTransacao(Transacao transacao) throws Exception {
        try {
            if (transacao == null || transacao.getVendaId() == null || transacao.getTipoPagamentoId() == null) {
                throw new Exception("Transação inválida ou dados ausentes");
            }
            if (!List.of("pending", "completed", "failed").contains(transacao.getEstado())) {
                throw new Exception("Estado de transação inválido");
            }
            Long transacaoId = transacaoRepositoryPort.saveTransacao(transacao);
            if (transacaoId == null) {
                throw new Exception("Falha ao criar transação");
            }
            return transacaoId;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    @Transactional
    public void mudarStatusTransacao(Long transacaoId, String estado) throws Exception {
        try {
            if (!List.of("pending", "completed", "failed").contains(estado)) {
                throw new Exception("Estado de transação inválido");
            }
            transacaoRepositoryPort.updateTransacaoStatus(transacaoId, estado);
        } catch (Exception e) {
            throw e;
        }
    }
}