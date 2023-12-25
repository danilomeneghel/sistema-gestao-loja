package loja.service;

import loja.entity.ProdutoEntity;
import loja.entity.VendaEntity;
import loja.model.Categoria;
import loja.model.Produto;
import loja.model.Venda;
import loja.repository.VendaRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VendaService {

    @Autowired
    private VendaRepository vendaRepository;

    @Autowired
    private ProdutoService produtoService;

    private ModelMapper modelMapper = new ModelMapper();

    public List<Venda> findAllVendas() {
        List<VendaEntity> vendasEntiity = vendaRepository.findAll();
        List<Venda> vendas = vendasEntiity.stream().map(entity -> modelMapper.map(entity, Venda.class)).collect(Collectors.toList());
        for (Venda venda : vendas) {
            StringBuilder produtos = new StringBuilder();
            for (Produto produto : venda.getProdutos()) {
                produtos.append(produto.getNome()).append(", ");
            }
            if (produtos.length() > 0) {
                produtos.delete(produtos.length() - 2, produtos.length());
            }
            venda.setProdutoString(produtos.toString());
        }
        return vendas;
    }

    public List<Venda> findVendasByConfirmadas() {
        List<VendaEntity> vendas = vendaRepository.findByStatusTrue();
        return vendas.stream().map(entity -> modelMapper.map(entity, Venda.class)).collect(Collectors.toList());
    }

    public Venda findVendaById(Long id) {
        if (id != null) {
            VendaEntity vendaEntity = vendaRepository.findById(id).orElse(new VendaEntity());
            return modelMapper.map(vendaEntity, Venda.class);
        }
        return null;
    }

    public List<Venda> findVendasByConfirmadasAndCategoria(Categoria categoria) {
        if (categoria.getId() != null) {
            List<VendaEntity> vendasConfirmadas = vendaRepository.findByStatusTrue();
            List<VendaEntity> vendasCategoria = new ArrayList<>();
            if (!vendasConfirmadas.isEmpty()) {
                for (VendaEntity vendaEntity : vendasConfirmadas) {
                    for (ProdutoEntity produtoEntity : vendaEntity.getProdutos()) {
                        if (produtoEntity.getCategoria().getNome() == categoria.getNome()) {
                            vendasCategoria.add(vendaEntity);
                        }
                    }
                }
                return vendasCategoria.stream().map(entity -> modelMapper.map(entity, Venda.class)).collect(Collectors.toList());
            }
        }
        return null;
    }

    public Venda salvarVenda(Venda venda) {
        List<Produto> produtoList = new ArrayList<>();
        String[] produtoArray = venda.getProdutoArray();
        int qtdeItensArray = produtoArray.length;
        if (qtdeItensArray > 0) {
            for (int i = 0; i < qtdeItensArray; i++) {
                String nome = produtoArray[i];
                produtoList.add(produtoService.findProdutoByNome(nome));
            }
        }
        venda.setProdutos(produtoList);
        VendaEntity vendaEntity = modelMapper.map(venda, VendaEntity.class);
        VendaEntity salvarVenda = vendaRepository.save(vendaEntity);
        return modelMapper.map(salvarVenda, Venda.class);
    }

    public void excluirVendaById(Long id) {
        if (id != null) {
            vendaRepository.deleteById(id);
        }
    }

}
