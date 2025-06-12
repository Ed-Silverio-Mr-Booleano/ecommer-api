package ao.bilabila.ecommerce.api.core.domain.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.sql.Timestamp;

@Data
public class Venda {
    private Long id;
    private Long clienteId;
    private String estado; // progress, ready, delivered, completed
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private Timestamp dataVendaInicio;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private Timestamp dataVendaFinal;
}