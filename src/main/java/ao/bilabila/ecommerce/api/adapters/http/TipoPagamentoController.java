package ao.bilabila.ecommerce.api.adapters.http;

import ao.bilabila.ecommerce.api.core.domain.models.TipoPagamento;
import ao.bilabila.ecommerce.api.ports.in.ITipoPagamentoUseCasePort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/tipos-pagamento")
public class TipoPagamentoController {

    private final ITipoPagamentoUseCasePort tipoPagamentoUseCase;

    public TipoPagamentoController(ITipoPagamentoUseCasePort tipoPagamentoUseCase) {
        this.tipoPagamentoUseCase = tipoPagamentoUseCase;
    }

    @GetMapping
    public ResponseEntity<List<TipoPagamento>> listarTiposPagamento() {
        try {
            List<TipoPagamento> tipos = tipoPagamentoUseCase.listarTiposPagamento();
            return ResponseEntity.ok(tipos);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}