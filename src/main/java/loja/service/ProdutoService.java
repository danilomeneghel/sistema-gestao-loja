package loja.service;

import loja.entity.ProdutoEntity;
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

    private ModelMapper modelMapper = new ModelMapper();

    public List<Produto> findAllProdutos() {
        List<ProdutoEntity> produtos = produtoRepository.findAll();
        return produtos.stream().map(entity -> modelMapper.map(entity, Produto.class)).collect(Collectors.toList());
    }

    public Produto findProdutoById(Long id) {
        Optional<ProdutoEntity> produtoEntity = produtoRepository.findById(id);
        if (!produtoEntity.isEmpty()) {
            return modelMapper.map(produtoEntity.get(), Produto.class);
        }
        return null;
    }

    public Produto salvarProduto(Produto produto) {
        ProdutoEntity produtoEntity = modelMapper.map(produto, ProdutoEntity.class);
        ProdutoEntity salvarProduto = produtoRepository.save(produtoEntity);
        return modelMapper.map(salvarProduto, Produto.class);
    }

    public void excluirProduto(Long id) {
        produtoRepository.deleteById(id);
    }

    public List<Produto> findProdutoByNome(String nome) {
        List<ProdutoEntity> produtos = produtoRepository.findByNomeContainingIgnoreCase(nome);
        return produtos.stream().map(entity -> modelMapper.map(entity, Produto.class)).collect(Collectors.toList());
    }

}
