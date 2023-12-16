package loja.model;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Produto {

    private Long id;

    private Categoria categoria;

    private Fornecedor fornecedor;

    private String nome;

    private BigDecimal preco;

    private List<Imagem> imagens;

    private MultipartFile[] files;

}
