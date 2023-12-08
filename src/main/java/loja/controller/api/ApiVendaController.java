package loja.controller.api;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import loja.model.Venda;
import loja.service.VendaService;

import java.util.List;

@RestController
@RequestMapping("/api/venda")
@Tag(name = "Venda")
@Validated
public class ApiVendaController {

    @Autowired
    private VendaService vendaService;

    @GetMapping("/vendas")
    public ResponseEntity<List<Venda>> mostrarImoveis() {
        return new ResponseEntity<>(vendaService.findAllVendas(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Venda> buscarVendaPorId(@PathVariable Long id) {
        return new ResponseEntity<>(vendaService.findVendaById(id), HttpStatus.OK);
    }

    @PostMapping("/venda/cadastro")
    public ResponseEntity<Venda> cadastrarVenda(@RequestBody Venda venda) {
        return new ResponseEntity<>(vendaService.salvarVenda(venda), HttpStatus.CREATED);
    }

    @PutMapping("/venda/editar/{id}")
    public ResponseEntity<Venda> editarVenda(@PathVariable Long id, @RequestBody Venda venda) {
        Venda imo = vendaService.findVendaById(id);
        if (imo == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        venda.setId(imo.getId());
        return new ResponseEntity<>(vendaService.salvarVenda(venda), HttpStatus.OK);
    }

    @DeleteMapping("/venda/excluir/{id}")
    public void excluirVenda(@PathVariable Long id) {
        vendaService.excluirVendaById(id);
    }

}
