package ao.bilabila.ecommerce.api.adapters.db.repository;

import ao.bilabila.ecommerce.api.core.domain.models.Categoria;
import ao.bilabila.ecommerce.api.core.domain.models.Produto;
import ao.bilabila.ecommerce.api.ports.out.IProdutoRepositoryPort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

public class ProdutoRepository implements IProdutoRepositoryPort {

    private final JdbcTemplate jdbcTemplate;

    public ProdutoRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Produto save(Produto produto) {
        String sql = "INSERT INTO produto (produto, preco, categoria_id, estoque) VALUES (?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, produto.getNome());
            ps.setDouble(2, produto.getPreco());
            ps.setLong(3, produto.getCategoria().getId());
            ps.setDouble(4, produto.getEstoque());
            return ps;
        }, keyHolder);

        produto.setId(keyHolder.getKey().longValue());
        return produto;
    }

    @Override
    public List<Produto> findAll() {
        String sql = "SELECT p.id, p.produto, p.preco, p.estoque, c.id AS categoria_id, c.categoria " +
                "FROM produto p LEFT JOIN categoria c ON p.categoria_id = c.id";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Produto prod = new Produto();
            prod.setId(rs.getLong("id"));
            prod.setNome(rs.getString("produto"));
            prod.setPreco(rs.getDouble("preco"));
            prod.setEstoque(rs.getObject("estoque") != null ? rs.getInt("estoque") : 0);

            Long categoriaId = rs.getObject("categoria_id") != null ? rs.getLong("categoria_id") : null;
            String categoriaNome = rs.getString("categoria");
            if (categoriaId != null && categoriaNome != null) {
                Categoria categoria = new Categoria();
                categoria.setId(categoriaId);
                categoria.setCategoria(categoriaNome);
                prod.setCategoria(categoria);
            }
            return prod;
        });
    }

    public Produto findById(Long id) {
        String sql = "SELECT p.id, p.produto, p.preco, p.estoque, c.id AS categoria_id, c.categoria " +
                "FROM produto p LEFT JOIN categoria c ON p.categoria_id = c.id WHERE p.id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, (rs, rowNum) -> {
            Produto prod = new Produto();
            prod.setId(rs.getLong("id"));
            prod.setNome(rs.getString("produto"));
            prod.setPreco(rs.getDouble("preco"));
            prod.setEstoque(rs.getInt("estoque"));

            Long categoriaId = rs.getObject("categoria_id") != null ? rs.getLong("categoria_id") : null;
            String categoriaNome = rs.getString("categoria");
            if (categoriaId != null && categoriaNome != null) {
                Categoria categoria = new Categoria();
                categoria.setId(categoriaId);
                categoria.setCategoria(categoriaNome);
                prod.setCategoria(categoria);
            }
            return prod;
        });
    }
}