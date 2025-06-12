package ao.bilabila.ecommerce.api.adapters.http;

import ao.bilabila.ecommerce.api.core.domain.models.OrderRequest;
import ao.bilabila.ecommerce.api.core.domain.models.Venda;
import ao.bilabila.ecommerce.api.core.domain.models.VendaProduto;
import ao.bilabila.ecommerce.api.ports.in.IVendaUseCasePort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vendas")
public class VendaController {

    private final IVendaUseCasePort vendaUseCase;

    public VendaController(IVendaUseCasePort vendaUseCase) {
        this.vendaUseCase = vendaUseCase;
    }

    @PostMapping
    public ResponseEntity<Long> criarOrder(@RequestBody OrderRequest orderRequest) {
        try {
            Long orderId = vendaUseCase.criarOrder(orderRequest.getVenda(), orderRequest.getVendaProdutos());
            return ResponseEntity.ok(orderId);
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping
    public ResponseEntity<List<Venda>> listarVendas() {
        try {
            List<Venda> vendas = vendaUseCase.listarVendas();
            return ResponseEntity.ok(vendas);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Void> mudarStatusOrder(@PathVariable Long id, @RequestBody String estado) {
        try {
            vendaUseCase.mudarStatusOrder(id, estado);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}