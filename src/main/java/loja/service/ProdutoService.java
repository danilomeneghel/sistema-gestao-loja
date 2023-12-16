package loja.service;

import loja.entity.CategoriaEntity;
import loja.entity.ProdutoEntity;
import loja.model.Categoria;
import loja.model.Produto;
import loja.repository.ProdutoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private ImagemService imagemService;

    private ModelMapper modelMapper = new ModelMapper();

    public List<Produto> findAllProdutos() {
        List<ProdutoEntity> produto = produtoRepository.findAll();
        return produto.stream().map(entity -> modelMapper.map(entity, Produto.class)).collect(Collectors.toList());
    }

    public Produto findProdutoById(Long id) {
        Optional<ProdutoEntity> produtoEntity = produtoRepository.findById(id);
        if (!produtoEntity.isEmpty()) {
            return modelMapper.map(produtoEntity.get(), Produto.class);
        }
        return null;
    }

    public List<Produto> findProdutoByCategoria(Categoria categoria) {
        CategoriaEntity categoriaEntity = modelMapper.map(categoria, CategoriaEntity.class);
        List<ProdutoEntity> produto = produtoRepository.findByCategoria(categoriaEntity);
        if (!produto.isEmpty()) {
            produto.stream().map(entity -> modelMapper.map(entity, Produto.class)).collect(Collectors.toList());
        }
        return null;
    }

    public Produto salvarProduto(Produto produto) {
        ProdutoEntity produtoEntity = modelMapper.map(produto, ProdutoEntity.class);
        ProdutoEntity salvarProduto = produtoRepository.save(produtoEntity);
        if (salvarProduto != null) {
            imagemService.armazenarImagem(salvarProduto.getId(), produto.getFiles());
        }
        return modelMapper.map(salvarProduto, Produto.class);
    }

    public void excluirProduto(Long id) {
        produtoRepository.deleteById(id);
    }

    public List<Produto> findProdutoByNome(String nome) {
        List<ProdutoEntity> produto = produtoRepository.findByNomeContainingIgnoreCase(nome);
        return produto.stream().map(entity -> modelMapper.map(entity, Produto.class)).collect(Collectors.toList());
    }

    public Produto findProdutoNome(String nome) {
        ProdutoEntity produtoEntity = produtoRepository.findByNome(nome);
        return modelMapper.map(produtoEntity, Produto.class);
    }

}
