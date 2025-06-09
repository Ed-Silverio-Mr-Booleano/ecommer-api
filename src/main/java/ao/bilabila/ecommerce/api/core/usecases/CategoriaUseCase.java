package ao.bilabila.ecommerce.api.core.usecases;

import ao.bilabila.ecommerce.api.core.domain.models.Categoria;
import ao.bilabila.ecommerce.api.ports.in.ICategoriaUseCasePort;
import ao.bilabila.ecommerce.api.ports.out.ICategoriaRepositoryPort;

import java.util.List;

public class CategoriaUseCase implements ICategoriaUseCasePort {

    private final ICategoriaRepositoryPort categoriaRepositoryPort;

    public CategoriaUseCase(ICategoriaRepositoryPort categoriaRepositoryPort) {
        this.categoriaRepositoryPort = categoriaRepositoryPort;
    }

    @Override
    public Categoria saveCategoria(Categoria categoria) {
        try {
            return categoriaRepositoryPort.save(categoria);
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public List<Categoria> findAllCategorias() {
        try {
            List<Categoria> categorias = categoriaRepositoryPort.findAll();

            return categorias;
        } catch (Exception e) {
            throw e;
        }
    }
}
