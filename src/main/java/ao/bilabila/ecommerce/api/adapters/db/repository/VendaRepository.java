package ao.bilabila.ecommerce.api.adapters.db.repository;

import ao.bilabila.ecommerce.api.core.domain.models.Transacao;
import ao.bilabila.ecommerce.api.core.domain.models.Venda;
import ao.bilabila.ecommerce.api.core.domain.models.VendaProduto;
import ao.bilabila.ecommerce.api.core.domain.models.VendaResponse;
import ao.bilabila.ecommerce.api.ports.out.IVendaRepositoryPort;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        System.out.println("repository: " + estado);
        String sql = "UPDATE venda SET estado = ? WHERE id = ?";
        jdbcTemplate.update(sql, estado, vendaId);
    }

    @Override
    public List<Venda> findAllVendas() {
        String sql = "SELECT v.id, v.cliente_id, v.estado, v.data_venda_inicio, v.data_venda_final, " +
                "vp.venda_id, vp.produto_id, vp.quantidade_comprada, " +
                "t.id as transacao_id, t.venda_id as transacao_venda_id, t.tipo_pagamento_id, t.estado as transacao_estado " +
                "FROM venda v " +
                "LEFT JOIN venda_produto vp ON v.id = vp.venda_id " +
                "LEFT JOIN transacao t ON v.id = t.venda_id";

        Map<Long, Venda> vendaMap = new HashMap<>();
        jdbcTemplate.query(sql, (rs, rowNum) -> mapToVenda(rs, vendaMap));
        return new ArrayList<>(vendaMap.values());
    }

    @Override
    public List<Venda> findVendasByClienteId(Long clienteId) {
        String sql = "SELECT v.id, v.cliente_id, v.estado, v.data_venda_inicio, v.data_venda_final, " +
                "vp.venda_id, vp.produto_id, vp.quantidade_comprada, " +
                "t.id as transacao_id, t.venda_id as transacao_venda_id, t.tipo_pagamento_id, t.estado as transacao_estado " +
                "FROM venda v " +
                "LEFT JOIN venda_produto vp ON v.id = vp.venda_id " +
                "LEFT JOIN transacao t ON v.id = t.venda_id " +
                "WHERE v.cliente_id = ?";

        Map<Long, Venda> vendaMap = new HashMap<>();
        jdbcTemplate.query(sql, new Object[]{clienteId}, (rs, rowNum) -> mapToVenda(rs, vendaMap));
        return new ArrayList<>(vendaMap.values());
    }

    @Override
    public Venda findVendaById(Long vendaId) {
        String sql = "SELECT v.id, v.cliente_id, v.estado, v.data_venda_inicio, v.data_venda_final, " +
                "vp.venda_id, vp.produto_id, vp.quantidade_comprada, " +
                "t.id as transacao_id, t.venda_id as transacao_venda_id, t.tipo_pagamento_id, t.estado as transacao_estado " +
                "FROM venda v " +
                "LEFT JOIN venda_produto vp ON v.id = vp.venda_id " +
                "LEFT JOIN transacao t ON v.id = t.venda_id " +
                "WHERE v.id = ?";

        Map<Long, Venda> vendaMap = new HashMap<>();
        jdbcTemplate.query(sql, new Object[]{vendaId}, (rs, rowNum) -> mapToVenda(rs, vendaMap));
        return vendaMap.get(vendaId);
    }

    private Venda mapToVenda(ResultSet rs, Map<Long, Venda> vendaMap) throws SQLException {
        Long vendaId = rs.getLong("id");

        // Obtém ou cria a instância de Venda para o vendaId
        Venda venda = vendaMap.computeIfAbsent(vendaId, k -> {
            Venda v = new Venda();
            try {
                v.setId(vendaId);
                v.setClienteId(rs.getLong("cliente_id"));
                v.setEstado(rs.getString("estado"));
                v.setDataVendaInicio(rs.getTimestamp("data_venda_inicio"));
                v.setDataVendaFinal(rs.getTimestamp("data_venda_final"));
                v.setVendaProdutos(new ArrayList<>());
            } catch (SQLException e) {
                v.setClienteId(null);
                v.setEstado(null);
                v.setDataVendaInicio(null);
                v.setDataVendaFinal(null);
                v.setVendaProdutos(new ArrayList<>());
                System.out.println("Erro ao mapear venda " + vendaId + ": " + e.getMessage()); // Depuração
            }
            return v;
        });

        // Adiciona produtos à mesma instância de Venda
        Long vendaProdutoVendaId = rs.getLong("venda_id");
        if (vendaProdutoVendaId != 0 && rs.getObject("produto_id") != null) {
            try {
                VendaProduto vp = new VendaProduto();
                vp.setVendaId(vendaProdutoVendaId);
                vp.setProdutoId(rs.getLong("produto_id"));
                vp.setQuantidadeComprada(rs.getInt("quantidade_comprada"));
                venda.getVendaProdutos().add(vp);
            } catch (SQLException e) {
                System.out.println("Erro ao mapear produto para venda " + vendaId + ": " + e.getMessage()); // Depuração
            }
        }

        // Adiciona transação à mesma instância de Venda (única por vendaId)
        Long transacaoId = rs.getLong("transacao_id");
        if (transacaoId != 0 && rs.getObject("transacao_venda_id") != null && venda.getTransacao() == null) {
            try {
                Transacao transacao = new Transacao();
                transacao.setId(transacaoId);
                transacao.setVendaId(rs.getLong("transacao_venda_id"));
                transacao.setTipoPagamentoId(rs.getLong("tipo_pagamento_id"));
                transacao.setEstado(rs.getString("transacao_estado"));
                venda.setTransacao(transacao);
            } catch (SQLException e) {
                System.out.println("Erro ao mapear transação para venda " + vendaId + ": " + e.getMessage()); // Depuração
            }
        }

        return venda;
    }

    @Override
    public VendaResponse convertToVendaResponse(Venda venda) {
        VendaResponse response = new VendaResponse();
        response.setId(venda.getId());
        response.setClienteId(venda.getClienteId());
        response.setEstado(venda.getEstado());
        response.setDataVendaInicio(venda.getDataVendaInicio());
        response.setDataVendaFinal(venda.getDataVendaFinal());

        // Converter vendaProdutos
        List<VendaResponse.VendaProdutoResponse> produtoResponses = venda.getVendaProdutos().stream().map(vp -> {
            VendaResponse.VendaProdutoResponse pr = new VendaResponse.VendaProdutoResponse();
            pr.setProdutoId(vp.getProdutoId());
            pr.setQuantidadeComprada(vp.getQuantidadeComprada());
            return pr;
        }).toList();
        response.setProdutos(produtoResponses);

        // Converter transacao
        if (venda.getTransacao() != null) {
            VendaResponse.TransacaoResponse tr = new VendaResponse.TransacaoResponse();
            tr.setId(venda.getTransacao().getId());
            tr.setTipoPagamentoId(venda.getTransacao().getTipoPagamentoId());
            tr.setEstado(venda.getTransacao().getEstado());
            response.setTransacao(tr);
        }

        return response;
    }
}