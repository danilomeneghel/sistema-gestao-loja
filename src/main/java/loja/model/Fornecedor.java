package loja.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Fornecedor {

    private Long id;

    private boolean ativo;

    private String nome;

    private String email;

    private String telefone;

    private String rua;

    private Bairro bairro;

    private Municipio municipio;

    private Estado estado;

}
