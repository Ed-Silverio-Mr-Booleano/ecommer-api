package ao.bilabila.ecommerce.api.core.domain.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class OrderRequest {
    private Venda venda;
    private List<VendaProduto> vendaProdutos;
}