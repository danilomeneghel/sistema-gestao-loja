package loja.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum StatusVenda {

    CANCELADA(false, "Cancelada"),
    CONFIRMADA(true, "Confirmada");

    private boolean valor;
    private String nome;

}
