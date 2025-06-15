package ao.bilabila.ecommerce.api.core.domain.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class VendaResponse {
    private Long id;
    private Long clienteId;
    private String estado;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private Timestamp dataVendaInicio;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private Timestamp dataVendaFinal;
    private List<VendaProdutoResponse> produtos; // Resumo dos produtos
    private TransacaoResponse transacao; // Resumo da transação

    // Classe interna para VendaProdutoResponse
    @Data
    public static class VendaProdutoResponse {
        private Long produtoId;
        private Integer quantidadeComprada;
    }

    // Classe interna para TransacaoResponse
    @Data
    public static class TransacaoResponse {
        private Long id;
        private Long tipoPagamentoId;
        private String estado;
    }
}