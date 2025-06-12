package ao.bilabila.ecommerce.api.adapters.db.repository;

import ao.bilabila.ecommerce.api.core.domain.models.Venda;
import ao.bilabila.ecommerce.api.core.domain.models.VendaProduto;
import ao.bilabila.ecommerce.api.ports.out.IVendaRepositoryPort;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class VendaRepository implements IVendaRepositoryPort {

    private final JdbcTemplate jdbcTemplate;

    public VendaRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Long saveVenda(Venda venda) {
        String sql = "INSERT INTO venda (cliente_id, estado, data_venda_inicio, data_venda_final) VALUES (?, ?, ?, ?) " +
                "ON DUPLICATE KEY UPDATE cliente_id = ?, estado = ?, data_venda_inicio = ?, data_venda_final = ?";
        jdbcTemplate.update(sql,
                venda.getClienteId(), venda.getEstado(), venda.getDataVendaInicio(), venda.getDataVendaFinal(),
                venda.getClienteId(), venda.getEstado(), venda.getDataVendaInicio(), venda.getDataVendaFinal());
        return jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Long.class);
    }

    @Override
    public void saveVendaProduto(VendaProduto vendaProduto) {
        String sql = "INSERT INTO venda_produto (venda_id, produto_id, quantidade_comprada) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, vendaProduto.getVendaId(), vendaProduto.getProdutoId(), vendaProduto.getQuantidadeComprada());
    }

    @Override
    public void updateVendaStatus(Long vendaId, String estado) {
        String sql = "UPDATE venda SET estado = ? WHERE id = ?";
        jdbcTemplate.update(sql, estado, vendaId);
    }

    @Override
    public List<Venda> findAllVendas() {
        String sql = "SELECT v.id, v.cliente_id, v.estado, v.data_venda_inicio, v.data_venda_final " +
                "FROM venda v";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Venda venda = new Venda();
            venda.setId(rs.getLong("id"));
            venda.setClienteId(rs.getLong("cliente_id"));
            venda.setEstado(rs.getString("estado"));
            venda.setDataVendaInicio(rs.getTimestamp("data_venda_inicio"));
            venda.setDataVendaFinal(rs.getTimestamp("data_venda_final"));
            return venda;
        });
    }
}