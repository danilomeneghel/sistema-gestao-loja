package loja.entity;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "venda")
@Data
public class VendaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_estabelecimento")
    private EstabelecimentoEntity estabelecimento;

    @ManyToMany
    @JoinTable(
            name = "venda_item",
            joinColumns = @JoinColumn(name = "id_venda"),
            inverseJoinColumns = @JoinColumn(name = "id_produto_item")
    )

    private List<ProdutoItemEntity> produtoItens;

    private String observacao;

    @NotNull(message = "O total do venda não pode estar em branco.")
    @DecimalMin(value = "0.01", message = "O total não pode ser R$0.00 ou negativo.")
    @DecimalMax(value = "99999999.99", message = "O total não pode ser maior que R$10000000.00")
    private BigDecimal total;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate data;

    private boolean status;

}