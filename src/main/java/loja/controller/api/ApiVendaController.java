package loja.controller.api;

import io.swagger.v3.oas.annotations.tags.Tag;
import loja.model.Categoria;
import loja.model.Venda;
import loja.service.CategoriaService;
import loja.service.VendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/venda")
@Tag(name = "Venda")
@Validated
public class ApiVendaController {

    @Autowired
    private VendaService vendaService;

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping("/vendas")
    public ResponseEntity<List<Venda>> mostrarVendas() {
        return new ResponseEntity<>(vendaService.findAllVendas(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Venda> buscarVendaPorId(@PathVariable Long id) {
        return new ResponseEntity<>(vendaService.findVendaById(id), HttpStatus.OK);
    }

    @PostMapping("/cadastro")
    public ResponseEntity<Venda> cadastrarVenda(@RequestBody Venda venda) {
        return new ResponseEntity<>(vendaService.salvarVenda(venda), HttpStatus.CREATED);
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<Venda> editarVenda(@PathVariable Long id, @RequestBody Venda venda) {
        Venda imo = vendaService.findVendaById(id);
        if (imo == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        venda.setId(imo.getId());
        return new ResponseEntity<>(vendaService.salvarVenda(venda), HttpStatus.OK);
    }

    @DeleteMapping("/excluir/{id}")
    public void excluirVenda(@PathVariable Long id) {
        vendaService.excluirVendaById(id);
    }

    @GetMapping("/pesquisa-categoria")
    public ResponseEntity<List<Venda>> pesquisarVenda(String pesquisa) {
        Categoria categoria = categoriaService.findCategoriaByNome(pesquisa);
        return new ResponseEntity<>(vendaService.findVendasByConfirmadasAndCategoria(categoria), HttpStatus.OK);
    }

}
