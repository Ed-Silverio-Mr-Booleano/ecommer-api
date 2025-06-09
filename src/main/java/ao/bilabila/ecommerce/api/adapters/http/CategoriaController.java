package ao.bilabila.ecommerce.api.adapters.http;

import ao.bilabila.ecommerce.api.core.domain.models.Categoria;
import ao.bilabila.ecommerce.api.core.usecases.CategoriaUseCase;
import ao.bilabila.ecommerce.api.ports.in.ICategoriaUseCasePort;
import ao.bilabila.ecommerce.api.ports.out.ICategoriaRepositoryPort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {

    private final ICategoriaUseCasePort categoriaUseCase;

    public CategoriaController(ICategoriaUseCasePort categoriaUseCase) {
        this.categoriaUseCase = categoriaUseCase;
    }

    @PostMapping
    public ResponseEntity<Categoria> cadastrarCategoria(@RequestBody Categoria categoria) {
        try {
            Categoria categoriaSalva = categoriaUseCase.saveCategoria(categoria);
            return ResponseEntity.ok(categoriaSalva);
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping
    public ResponseEntity<List<Categoria>> listarCategorias() {
        try {
            List<Categoria> categorias = categoriaUseCase.findAllCategorias();
            return ResponseEntity.ok(categorias);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}