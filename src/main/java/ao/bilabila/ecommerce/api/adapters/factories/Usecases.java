package ao.bilabila.ecommerce.api.adapters.factories;

import ao.bilabila.ecommerce.api.adapters.db.repository.CategoriaRepository;
import ao.bilabila.ecommerce.api.adapters.db.repository.ProdutoRepository;
import ao.bilabila.ecommerce.api.adapters.db.repository.TipoPagamentoRepository;
import ao.bilabila.ecommerce.api.adapters.db.repository.TransacaoRepository;
import ao.bilabila.ecommerce.api.adapters.db.repository.VendaRepository;
import ao.bilabila.ecommerce.api.core.usecases.CategoriaUseCase;
import ao.bilabila.ecommerce.api.core.usecases.ProdutoUseCase;
import ao.bilabila.ecommerce.api.core.usecases.TipoPagamentoUseCase;
import ao.bilabila.ecommerce.api.core.usecases.TransacaoUseCase;
import ao.bilabila.ecommerce.api.core.usecases.VendaUseCase;
import ao.bilabila.ecommerce.api.ports.in.ICategoriaUseCasePort;
import ao.bilabila.ecommerce.api.ports.in.IProdutoUseCasePort;
import ao.bilabila.ecommerce.api.ports.in.ITipoPagamentoUseCasePort;
import ao.bilabila.ecommerce.api.ports.in.ITransacaoUseCasePort;
import ao.bilabila.ecommerce.api.ports.in.IVendaUseCasePort;
import ao.bilabila.ecommerce.api.ports.out.ICategoriaRepositoryPort;
import ao.bilabila.ecommerce.api.ports.out.IProdutoRepositoryPort;
import ao.bilabila.ecommerce.api.ports.out.ITipoPagamentoRepositoryPort;
import ao.bilabila.ecommerce.api.ports.out.ITransacaoRepositoryPort;
import ao.bilabila.ecommerce.api.ports.out.IVendaRepositoryPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class Usecases {

    @Bean
    public ICategoriaRepositoryPort categoriaRepository(JdbcTemplate jdbcTemplate) {
        return new CategoriaRepository(jdbcTemplate);
    }

    @Bean
    public ICategoriaUseCasePort categoriaUseCasePort(ICategoriaRepositoryPort categoriaRepositoryPort) {
        return new CategoriaUseCase(categoriaRepositoryPort);
    }

    @Bean
    public IProdutoRepositoryPort produtoRepository(JdbcTemplate jdbcTemplate) {
        return new ProdutoRepository(jdbcTemplate);
    }

    @Bean
    public IProdutoUseCasePort produtoUseCasePort(IProdutoRepositoryPort produtoRepositoryPort) {
        return new ProdutoUseCase(produtoRepositoryPort);
    }

    @Bean
    public IVendaRepositoryPort vendaRepository(JdbcTemplate jdbcTemplate) {
        return new VendaRepository(jdbcTemplate);
    }

    @Bean
    public IVendaUseCasePort vendaUseCasePort(IVendaRepositoryPort vendaRepositoryPort, IProdutoUseCasePort produtoUseCasePort, ITransacaoRepositoryPort transacaoRepositoryPort) {
        return new VendaUseCase(vendaRepositoryPort, produtoUseCasePort, transacaoRepositoryPort);
    }

    @Bean
    public ITransacaoRepositoryPort transacaoRepository(JdbcTemplate jdbcTemplate) {
        return new TransacaoRepository(jdbcTemplate);
    }

    @Bean
    public ITipoPagamentoRepositoryPort tipoPagamentoRepository(JdbcTemplate jdbcTemplate) {
        return new TipoPagamentoRepository(jdbcTemplate);
    }

    @Bean
    public ITipoPagamentoUseCasePort tipoPagamentoUseCasePort(ITipoPagamentoRepositoryPort tipoPagamentoRepositoryPort) {
        return new TipoPagamentoUseCase(tipoPagamentoRepositoryPort);
    }
}