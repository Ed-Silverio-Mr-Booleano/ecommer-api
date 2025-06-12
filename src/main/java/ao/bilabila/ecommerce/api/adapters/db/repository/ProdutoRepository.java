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
        String sql = "INSERT INTO produto (produto, preco, categoria_id, estoque, img) VALUES (?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, produto.getNome());
            ps.setDouble(2, produto.getPreco());
            ps.setLong(3, produto.getCategoria().getId());
            ps.setDouble(4, produto.getEstoque());
            ps.setString(5, produto.getImg());
            return ps;
        }, keyHolder);

        produto.setId(keyHolder.getKey().longValue());
        return produto;
    }

    @Override
    public List<Produto> findAll() {
        String sql = "SELECT p.id, p.produto, p.preco, p.estoque, p.img, c.id AS categoria_id, c.categoria " +
                "FROM produto p LEFT JOIN categoria c ON p.categoria_id = c.id";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Produto prod = new Produto();
            prod.setId(rs.getLong("id"));
            prod.setNome(rs.getString("produto"));
            prod.setPreco(rs.getDouble("preco"));
            prod.setEstoque(rs.getObject("estoque") != null ? rs.getInt("estoque") : 0);
            prod.setImg(rs.getString("img"));

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
  @Override
    public Produto findById(Long id) {
        String sql = "SELECT p.id, p.produto, p.preco, p.estoque, p.img, c.id AS categoria_id, c.categoria " +
                "FROM produto p LEFT JOIN categoria c ON p.categoria_id = c.id WHERE p.id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, (rs, rowNum) -> {
            Produto prod = new Produto();
            prod.setId(rs.getLong("id"));
            prod.setNome(rs.getString("produto"));
            prod.setPreco(rs.getDouble("preco"));
            prod.setEstoque(rs.getInt("estoque"));
            prod.setImg(rs.getString("img"));

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
 @Override
    public void deactivateProduto(Long id) {
        String sql = "UPDATE produto SET ativo = 0 WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
    @Override
    public void updateStock(Long produtoId, int quantidade) {
        String sql = "UPDATE produto SET estoque = estoque - ? WHERE id = ? AND estoque >= ?";
        jdbcTemplate.update(sql, quantidade, produtoId, quantidade);
    }
}