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
public class ProdutoItem {

    private Long id;

    private Categoria categoria;

    private String nome;

    private String produtos;

    private String[] produtosArray;

    private BigDecimal preco;

    private List<Imagem> imagens;

    private MultipartFile[] files;

}
