package ao.bilabila.ecommerce.api.adapters.factories;

import ao.bilabila.ecommerce.api.adapters.db.repository.CategoriaRepository;
import ao.bilabila.ecommerce.api.adapters.db.repository.ProdutoRepository;
import ao.bilabila.ecommerce.api.core.usecases.CategoriaUseCase;
import ao.bilabila.ecommerce.api.core.usecases.ProdutoUseCase;
import ao.bilabila.ecommerce.api.ports.in.ICategoriaUseCasePort;
import ao.bilabila.ecommerce.api.ports.in.IProdutoUseCasePort;
import ao.bilabila.ecommerce.api.ports.out.ICategoriaRepositoryPort;
import ao.bilabila.ecommerce.api.ports.out.IProdutoRepositoryPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class Usecases {
    @Bean
    public IProdutoRepositoryPort produtoRepository(JdbcTemplate jdbcTemplate) {
        return new ProdutoRepository(jdbcTemplate);
    }

    @Bean
    public IProdutoUseCasePort produtoUseCasePort(IProdutoRepositoryPort produtoRepositoryPort) {
        return new ProdutoUseCase(produtoRepositoryPort);
    }

    @Bean
    public ICategoriaRepositoryPort categoriaRepository(JdbcTemplate jdbcTemplate) {
        return new CategoriaRepository(jdbcTemplate);
    }

    @Bean
    public ICategoriaUseCasePort categoriaUseCasePort(ICategoriaRepositoryPort categoriaRepositoryPort) {
        return new CategoriaUseCase(categoriaRepositoryPort);
    }
}