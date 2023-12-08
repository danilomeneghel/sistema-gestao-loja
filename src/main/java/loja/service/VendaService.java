package loja.service;

import loja.model.Venda;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import loja.entity.ProdutoItemEntity;
import loja.entity.VendaEntity;
import loja.model.ProdutoItem;
import loja.model.Categoria;
import loja.repository.VendaRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VendaService {

    @Autowired
    private VendaRepository vendaRepository;

    @Autowired
    private ProdutoItemService produtoItemService;

    private ModelMapper modelMapper = new ModelMapper();

    public List<Venda> findAllVendas() {
        List<VendaEntity> vendasEntiity = vendaRepository.findAll();
        List<Venda> vendas = vendasEntiity.stream().map(entity -> modelMapper.map(entity, Venda.class)).collect(Collectors.toList());
        for(Venda venda : vendas) {
            StringBuilder produtoItens = new StringBuilder();
            for (ProdutoItem produtoItem : venda.getProdutoItens()) {
                produtoItens.append(produtoItem.getNome()).append(", ");
            }
            if (produtoItens.length() > 0) {
                produtoItens.delete(produtoItens.length() - 2, produtoItens.length());
            }
            venda.setProdutoItensString(produtoItens.toString());
        }
        return vendas;
    }

    public List<Venda> findVendasByConfirmado() {
        List<VendaEntity> vendas = vendaRepository.findByStatusTrue();
        return vendas.stream().map(entity -> modelMapper.map(entity, Venda.class)).collect(Collectors.toList());
    }

    public Venda findVendaById(Long id) {
        Optional<VendaEntity> vendaEntity = vendaRepository.findById(id);
        if (!vendaEntity.isEmpty()) {
            return modelMapper.map(vendaEntity.get(), Venda.class);
        }
        return null;
    }

    public List<Venda> findVendasByConfirmadoAndCategoria(Categoria categoria) {
        if (categoria.getId() != null) {
            List<VendaEntity> vendasConfirmados = vendaRepository.findByStatusTrue();
            List<VendaEntity> vendasCategoria = new ArrayList<>();
            if (!vendasConfirmados.isEmpty()) {
                for (VendaEntity vendaEntity : vendasConfirmados) {
                    for (ProdutoItemEntity produtoItemEntity : vendaEntity.getProdutoItens()) {
                        if (produtoItemEntity.getCategoria().getNome() == categoria.getNome()) {
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
        List<ProdutoItem> produtoItemList = new ArrayList<>();
        String[] produtoItensArray = venda.getProdutoItensArray();
        int qtdeItensArray = produtoItensArray.length;
        if (qtdeItensArray > 0) {
            for (int i = 0; i < qtdeItensArray; i++) {
                String nome = produtoItensArray[i];
                ProdutoItem produtoItem = produtoItemService.findProdutoItemNome(nome);
                produtoItemList.add(produtoItem);
            }
        }
        venda.setProdutoItens(produtoItemList);
        VendaEntity vendaEntity = modelMapper.map(venda, VendaEntity.class);
        VendaEntity salvarVenda = vendaRepository.save(vendaEntity);
        return modelMapper.map(salvarVenda, Venda.class);
    }

    public void excluirVendaById(Long id) {
        vendaRepository.deleteById(id);
    }

}
