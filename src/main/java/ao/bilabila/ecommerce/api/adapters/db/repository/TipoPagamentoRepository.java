package ao.bilabila.ecommerce.api.adapters.db.repository;

import ao.bilabila.ecommerce.api.core.domain.models.TipoPagamento;
import ao.bilabila.ecommerce.api.ports.out.ITipoPagamentoRepositoryPort;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class TipoPagamentoRepository implements ITipoPagamentoRepositoryPort {

    private final JdbcTemplate jdbcTemplate;

    public TipoPagamentoRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<TipoPagamento> findAll() {
        String sql = "SELECT id, pagamento FROM tipo_pagamento";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            TipoPagamento tipo = new TipoPagamento();
            tipo.setId(rs.getLong("id"));
            tipo.setPagamento(rs.getString("pagamento"));
            return tipo;
        });
    }
}