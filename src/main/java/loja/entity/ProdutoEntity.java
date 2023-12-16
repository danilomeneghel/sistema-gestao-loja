package loja.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "produto")
@Getter
@Setter
public class ProdutoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_categoria")
    private CategoriaEntity categoria;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_fornecedor")
    private FornecedorEntity fornecedor;

    @NotBlank(message = "O nome do item do cardápio não pode estar em branco.")
    private String nome;

    @NotNull(message = "O preço do item do cardápio não pode estar em branco.")
    @DecimalMin(value = "0.01", message = "O preço não pode ser R$0.00 ou negativo.")
    @DecimalMax(value = "99999999.99", message = "O preço não pode ser maior que R$10000000.00")
    private BigDecimal preco;

    @OneToMany(mappedBy = "produto", cascade = CascadeType.REMOVE)
    private List<ImagemEntity> imagens;

}
