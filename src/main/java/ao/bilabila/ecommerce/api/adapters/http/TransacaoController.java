package ao.bilabila.ecommerce.api.adapters.http;

import ao.bilabila.ecommerce.api.core.domain.models.Transacao;
import ao.bilabila.ecommerce.api.ports.in.ITransacaoUseCasePort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transacoes")
public class TransacaoController {

    private final ITransacaoUseCasePort transacaoUseCase;

    public TransacaoController(ITransacaoUseCasePort transacaoUseCase) {
        this.transacaoUseCase = transacaoUseCase;
    }


    @PutMapping("/{venda_id}/status")
    public ResponseEntity<Transacao> mudarStatusTransacao(@PathVariable Long venda_id, @RequestParam String estado) {
        try {
            transacaoUseCase.mudarStatusTransacao(venda_id, estado);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            System.out.println("transacoes: " + e);
            return ResponseEntity.badRequest().build();
        }
    }
}