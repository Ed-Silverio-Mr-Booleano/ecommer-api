package ao.bilabila.ecommerce.api.adapters.db.repository;

import ao.bilabila.ecommerce.api.core.domain.models.Categoria;
import ao.bilabila.ecommerce.api.ports.out.ICategoriaRepositoryPort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

public class CategoriaRepository implements ICategoriaRepositoryPort {

    private final JdbcTemplate jdbcTemplate;

    public CategoriaRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Categoria save(Categoria categoria) {
        String sql = "INSERT INTO categoria (categoria) VALUES (?) ON DUPLICATE KEY UPDATE categoria = ?";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, categoria.getCategoria());
            ps.setString(2, categoria.getCategoria());
            return ps;
        }, keyHolder);

        categoria.setId(keyHolder.getKey().longValue());
        return categoria;
    }

    @Override
    public List<Categoria> findAll() {
        String sql = "SELECT id, categoria FROM categoria";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Categoria cat = new Categoria();
            cat.setId(rs.getLong("id"));
            cat.setCategoria(rs.getString("categoria"));
            return cat;
        });
    }
}