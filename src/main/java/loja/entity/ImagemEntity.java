package loja.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "imagem")
@Data
public class ImagemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String file;
    
    @NotBlank
    private String path;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_produto_item", nullable = false)
    private ProdutoItemEntity produtoItem;

}
