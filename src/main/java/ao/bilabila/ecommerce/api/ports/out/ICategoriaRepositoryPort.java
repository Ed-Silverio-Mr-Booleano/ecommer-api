package ao.bilabila.ecommerce.api.ports.out;

import ao.bilabila.ecommerce.api.core.domain.models.Categoria;
import java.util.List;

public interface ICategoriaRepositoryPort {
    Categoria save(Categoria categoria);
    List<Categoria> findAll();
}
