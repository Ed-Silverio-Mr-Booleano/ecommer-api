package ao.bilabila.ecommerce.api.ports.in;

import ao.bilabila.ecommerce.api.core.domain.models.Categoria;
import java.util.List;

public interface ICategoriaUseCasePort {
    Categoria saveCategoria(Categoria categoria);
    List<Categoria> findAllCategorias();
}
