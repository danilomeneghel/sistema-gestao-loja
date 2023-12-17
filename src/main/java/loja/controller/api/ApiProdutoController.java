package loja.controller.api;

import io.swagger.v3.oas.annotations.tags.Tag;
import loja.model.Produto;
import loja.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/produto")
@Tag(name = "Produto")
@Validated
public class ApiProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @GetMapping("/produtos")
    public ResponseEntity<List<Produto>> mostrarProdutos() {
        return new ResponseEntity<>(produtoService.findAllProdutos(), HttpStatus.OK);
    }

    @PostMapping("/cadastro")
    public ResponseEntity<Produto> cadastroProduto(@RequestBody Produto produto) {
        return new ResponseEntity<>(produtoService.salvarProduto(produto), HttpStatus.CREATED);
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<Produto> editarProduto(@PathVariable Long id, @RequestBody Produto produto) {
        Produto cat = produtoService.findProdutoById(id);
        if (cat == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        produto.setId(cat.getId());
        return new ResponseEntity<>(produtoService.salvarProduto(produto), HttpStatus.OK);
    }

    @DeleteMapping("/excluir/{id}")
    public void excluirProduto(@PathVariable Long id) {
        produtoService.excluirProduto(id);
    }

    @GetMapping("/pesquisa")
    public ResponseEntity<List<Produto>> pesquisarProduto(String pesquisa) {
        return new ResponseEntity<>(produtoService.findProdutoByNomeIgnoreCase(pesquisa), HttpStatus.OK);
    }

}
