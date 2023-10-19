package tech.ada.ecommerce.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClienteEnderecoDTO {

    private Long clienteId;

    private Long enderecoId;

    private String tipo;

    private String nomeRecebedor;

}
