package ao.bilabila.ecommerce.api.adapters.db.repository;

import ao.bilabila.ecommerce.api.core.domain.models.Transacao;
import ao.bilabila.ecommerce.api.ports.out.ITransacaoRepositoryPort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.PreparedStatement;
import java.sql.Statement;

public class TransacaoRepository implements ITransacaoRepositoryPort {

    private final JdbcTemplate jdbcTemplate;

    public TransacaoRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Long saveTransacao(Transacao transacao) {
        String sql = "INSERT INTO transacao (venda_id, tipo_pagamento_id, estado) VALUES (?, ?, ?) " +
                "ON DUPLICATE KEY UPDATE venda_id = ?, tipo_pagamento_id = ?, estado = ?";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, transacao.getVendaId());
            ps.setLong(2, transacao.getTipoPagamentoId());
            ps.setString(3, transacao.getEstado());
            ps.setLong(4, transacao.getVendaId());
            ps.setLong(5, transacao.getTipoPagamentoId());
            ps.setString(6, transacao.getEstado());
            return ps;
        }, keyHolder);

        return keyHolder.getKey() != null ? keyHolder.getKey().longValue() : null;
    }

    @Override
    public void updateTransacaoStatus(Long vendaId, String estado) {
        String sql = "UPDATE transacao SET estado = ? WHERE venda_id = ?";
        jdbcTemplate.update(sql, estado, vendaId);
    }
}