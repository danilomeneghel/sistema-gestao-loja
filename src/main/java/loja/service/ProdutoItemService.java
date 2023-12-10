package loja.service;

import loja.entity.CategoriaEntity;
import loja.entity.ProdutoItemEntity;
import loja.mapper.ProdutoItemMapper;
import loja.model.Categoria;
import loja.model.Imagem;
import loja.model.ProdutoItem;
import loja.repository.ProdutoItemRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProdutoItemService implements ProdutoItemMapper {

    @Autowired
    private ProdutoItemRepository produtoItemRepository;

    @Autowired
    private ImagemService imagemService;

    private ModelMapper modelMapper = new ModelMapper();

    public List<ProdutoItem> findAllProdutoItens() {
        List<ProdutoItemEntity> produtoItens = produtoItemRepository.findAll();
        return produtoItens.stream().map(entity -> modelMapper.map(entity, ProdutoItem.class)).collect(Collectors.toList());
    }

    public ProdutoItem findProdutoItemById(Long id) {
        Optional<ProdutoItemEntity> produtoItemEntity = produtoItemRepository.findById(id);
        if (!produtoItemEntity.isEmpty()) {
            Categoria categoria = modelMapper.map(produtoItemEntity.get().getCategoria(), Categoria.class);
            List<Imagem> imagens = produtoItemEntity.get().getImagens().stream().map(entity -> modelMapper.map(entity, Imagem.class)).collect(Collectors.toList());
            ProdutoItem produtoItem = ProdutoItemMapper.setProdutoItem(produtoItemEntity.get(), categoria, imagens);
            return produtoItem;
        }
        return null;
    }

    public List<ProdutoItem> findProdutoItemByCategoria(Categoria categoria) {
        CategoriaEntity categoriaEntity = modelMapper.map(categoria, CategoriaEntity.class);
        List<ProdutoItemEntity> produtoItens = produtoItemRepository.findByCategoria(categoriaEntity);
        if (!produtoItens.isEmpty()) {
            produtoItens.stream().map(entity -> modelMapper.map(entity, ProdutoItem.class)).collect(Collectors.toList());
        }
        return null;
    }

    public ProdutoItem salvarProdutoItem(ProdutoItem produtoItem) {
        ProdutoItemEntity produtoItemEntity = modelMapper.map(produtoItem, ProdutoItemEntity.class);
        String produtos = String.join(", ", produtoItem.getProdutosArray());
        produtoItemEntity.setProdutos(produtos);
        ProdutoItemEntity salvarProdutoItem = produtoItemRepository.save(produtoItemEntity);
        if (salvarProdutoItem != null) {
            imagemService.armazenarImagem(salvarProdutoItem.getId(), produtoItem.getFiles());
        }
        return modelMapper.map(salvarProdutoItem, ProdutoItem.class);
    }

    public void excluirProdutoItem(Long id) {
        produtoItemRepository.deleteById(id);
    }

    public List<ProdutoItem> findProdutoItemByNome(String nome) {
        List<ProdutoItemEntity> produtoItens = produtoItemRepository.findByNomeContainingIgnoreCase(nome);
        return produtoItens.stream().map(entity -> modelMapper.map(entity, ProdutoItem.class)).collect(Collectors.toList());
    }

    public ProdutoItem findProdutoItemNome(String nome) {
        ProdutoItemEntity produtoItemEntity = produtoItemRepository.findByNome(nome);
        return modelMapper.map(produtoItemEntity, ProdutoItem.class);
    }

}
