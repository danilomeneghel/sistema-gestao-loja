package loja.controller.api;

import io.swagger.v3.oas.annotations.tags.Tag;
import loja.model.ProdutoItem;
import loja.service.ProdutoItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import loja.model.Produto;
import loja.service.ProdutoService;

import java.util.List;

@RestController
@RequestMapping("/api/produto")
@Tag(name = "Produto")
@Validated
public class ApiProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private ProdutoItemService produtoItemService;

    @GetMapping("/produtos")
    public ResponseEntity<List<Produto>> mostrarProdutos() {
        return new ResponseEntity<>(produtoService.findAllProdutos(), HttpStatus.OK);
    }

    @PostMapping("/produto/cadastro")
    public ResponseEntity<Produto> cadastroProduto(@RequestBody Produto produto) {
        return new ResponseEntity<>(produtoService.salvarProduto(produto), HttpStatus.CREATED);
    }

    @PutMapping("/produto/editar/{id}")
    public ResponseEntity<Produto> editarProduto(@PathVariable Long id, @RequestBody Produto produto) {
        Produto cat = produtoService.findProdutoById(id);
        if (cat == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        produto.setId(cat.getId());
        return new ResponseEntity<>(produtoService.salvarProduto(produto), HttpStatus.OK);
    }

    @DeleteMapping("/produto/excluir/{id}")
    public void excluirProduto(@PathVariable Long id) {
        produtoService.excluirProduto(id);
    }

    @GetMapping("/produto/pesquisa")
    public ResponseEntity<List<Produto>> pesquisarProduto(String pesquisa) {
        return new ResponseEntity<>(produtoService.findProdutoByNome(pesquisa), HttpStatus.OK);
    }

    @GetMapping("/produto-itens")
    public ResponseEntity<List<ProdutoItem>> mostrarProdutoItens() {
        return new ResponseEntity<>(produtoItemService.findAllProdutoItens(), HttpStatus.OK);
    }

    @PostMapping("/produto-item/cadastro")
    public ResponseEntity<ProdutoItem> cadastroProdutoItem(@RequestBody ProdutoItem produtoItem) {
        return new ResponseEntity<>(produtoItemService.salvarProdutoItem(produtoItem), HttpStatus.CREATED);
    }

    @PutMapping("/produto-item/editar/{id}")
    public ResponseEntity<ProdutoItem> editarProdutoItem(@PathVariable Long id, @RequestBody ProdutoItem produtoItem) {
        ProdutoItem cat = produtoItemService.findProdutoItemById(id);
        if (cat == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        produtoItem.setId(cat.getId());
        return new ResponseEntity<>(produtoItemService.salvarProdutoItem(produtoItem), HttpStatus.OK);
    }

    @DeleteMapping("/produto-item/excluir/{id}")
    public void excluirProdutoItem(@PathVariable Long id) {
        produtoItemService.excluirProdutoItem(id);
    }

    @GetMapping("/produto-item/pesquisa")
    public ResponseEntity<List<ProdutoItem>> pesquisarProdutoItem(String pesquisa) {
        return new ResponseEntity<>(produtoItemService.findProdutoItemByNome(pesquisa), HttpStatus.OK);
    }

}
