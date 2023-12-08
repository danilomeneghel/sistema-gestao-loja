package loja.mapper;

import loja.model.ProdutoItem;
import org.springframework.stereotype.Component;
import loja.entity.ProdutoItemEntity;
import loja.model.Categoria;
import loja.model.Imagem;

import java.util.List;

@Component
public interface ProdutoItemMapper {

    static ProdutoItem setProdutoItem(ProdutoItemEntity produtoItemEntity, Categoria categoria, List<Imagem> imagens) {
        ProdutoItem produtoItem = new ProdutoItem();
        produtoItem.setId(produtoItemEntity.getId());
        produtoItem.setCategoria(categoria);
        produtoItem.setNome(produtoItemEntity.getNome());
        produtoItem.setProdutos(produtoItemEntity.getProdutos());
        produtoItem.setPreco(produtoItemEntity.getPreco());
        produtoItem.setImagens(imagens);
        return produtoItem;
    }

}
