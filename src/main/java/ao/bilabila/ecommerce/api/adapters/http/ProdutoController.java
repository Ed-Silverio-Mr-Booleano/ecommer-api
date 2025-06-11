package ao.bilabila.ecommerce.api.adapters.http;

import ao.bilabila.ecommerce.api.core.domain.models.Produto;
import ao.bilabila.ecommerce.api.ports.in.IProdutoUseCasePort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

    private final IProdutoUseCasePort produtoUseCase;

    public ProdutoController(IProdutoUseCasePort produtoUseCase) {
        this.produtoUseCase = produtoUseCase;
    }

    @PostMapping
    public ResponseEntity<Produto> cadastrarProduto(@RequestBody Produto produto) {
        try {
            Produto produtoSalvo = produtoUseCase.saveProduto(produto);

            return ResponseEntity.ok(produtoSalvo);
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping
    public ResponseEntity<List<Produto>> listarProdutos() {
        try {
            List<Produto> produtos = produtoUseCase.findAllProdutos();
            return ResponseEntity.ok(produtos);
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Produto> getProdutoById(@PathVariable Long id) {
        try {
            Produto produto = produtoUseCase.findProdutoById(id);
            return ResponseEntity.ok(produto);
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.badRequest().body(null);
        }
    }
}