package ao.bilabila.ecommerce.api.adapters.http;

import ao.bilabila.ecommerce.api.core.domain.models.OrderRequest;
import ao.bilabila.ecommerce.api.core.domain.models.VendaResponse;
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
            Long orderId = vendaUseCase.criarOrder(
                    orderRequest.getVenda(),
                    orderRequest.getVendaProdutos(),
                    orderRequest.getTransacao()
            );
            return ResponseEntity.ok(orderId);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping
    public ResponseEntity<List<VendaResponse>> listarVendas() {
        try {
            List<VendaResponse> vendas = vendaUseCase.listarVendas();
            return ResponseEntity.ok(vendas);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/user/{clienteId}")
    public ResponseEntity<List<VendaResponse>> listarVendasPorUser(@PathVariable Long clienteId) {
        try {
            List<VendaResponse> vendas = vendaUseCase.listarVendasPorUser(clienteId);
            return ResponseEntity.ok(vendas);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/{vendaId}")
    public ResponseEntity<VendaResponse> listarVendaPorId(@PathVariable Long vendaId) {
        try {
            VendaResponse venda = vendaUseCase.listarVendaPorId(vendaId);
            return venda != null ? ResponseEntity.ok(venda) : ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Void> mudarStatusOrder(@PathVariable Long id, @RequestParam String estado) {
        try {
            vendaUseCase.mudarStatusOrder(id, estado);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.badRequest().build();
        }
    }
}