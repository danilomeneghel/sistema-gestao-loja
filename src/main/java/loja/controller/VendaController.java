package loja.controller;

import loja.model.Categoria;
import loja.model.Produto;
import loja.model.Venda;
import loja.service.CategoriaService;
import loja.service.EstabelecimentoService;
import loja.service.ProdutoService;
import loja.service.VendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/venda")
public class VendaController {

    @Autowired
    private VendaService vendaService;

    @Autowired
    private CategoriaService categoriaService;

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private EstabelecimentoService estabelecimentoService;

    @GetMapping("/vendas-confirmadas")
    public ModelAndView homeVendasUsuario() {
        ModelAndView mv = new ModelAndView("indexUsuario");
        mv.addObject("categorias", categoriaService.findAllCategorias());
        mv.addObject("vendas", vendaService.findVendasByConfirmadas());
        return mv;
    }

    @GetMapping("/vendas-confirmadas/{idCategoria}")
    public ModelAndView filtrarVendasUsuario(@PathVariable Long idCategoria) {
        ModelAndView mv = new ModelAndView("venda/vendasFiltro");
        if (idCategoria != 0) {
            Categoria categoria = categoriaService.findCategoriaById(idCategoria);
            mv.addObject("vendas", vendaService.findVendasByConfirmadasAndCategoria(categoria));
        } else {
            mv.addObject("vendas", vendaService.findVendasByConfirmadas());
        }
        mv.addObject("categorias", categoriaService.findAllCategorias());
        return mv;
    }

    @GetMapping("/vendas")
    public ModelAndView mostrarVendas() {
        ModelAndView mv = new ModelAndView("venda/vendas");
        mv.addObject("venda", vendaService.findAllVendas());
        return mv;
    }

    @GetMapping("/cadastro")
    public ModelAndView cadastroVenda() {
        ModelAndView mv = new ModelAndView("venda/vendaCadastro");

        mv.addObject("venda", new Venda());
        mv.addObject("estabelecimentos", estabelecimentoService.findAllEstabelecimentos());
        mv.addObject("categorias", categoriaService.findAllCategorias());
        mv.addObject("produtos", produtoService.findAllProdutos());
        return mv;
    }

    @PostMapping(value = "/cadastrar")
    public ModelAndView cadastrarVenda(@Validated Venda venda, Errors errors, Long idEstado, Long idMunicipio) {
        ModelAndView mv = new ModelAndView("venda/vendaCadastro");
        boolean erro = false;
        List<String> customMessage = new ArrayList<String>();

        if (venda.getProdutoArray() == null) {
            customMessage.add("Selecione um Produto.");
            mv.addObject("erroProduto", true);
            erro = true;
        }

        if (errors.hasErrors() || erro) {
            mv.addObject("customMessage", customMessage);
            return mv;
        }
        vendaService.salvarVenda(venda);

        mv.addObject("sucesso", "O Venda foi cadastrado com sucesso.");
        mv.addObject("venda", new Venda());
        mv.addObject("estabelecimentos", estabelecimentoService.findAllEstabelecimentos());
        mv.addObject("categorias", categoriaService.findAllCategorias());
        mv.addObject("produtos", produtoService.findAllProdutos());
        return mv;
    }

    @GetMapping("/editar/{id}")
    public ModelAndView editaVenda(@PathVariable Long id) {
        ModelAndView mv = new ModelAndView("venda/vendaEditar");

        mv.addObject("venda", vendaService.findVendaById(id));
        mv.addObject("estabelecimentos", estabelecimentoService.findAllEstabelecimentos());
        mv.addObject("categorias", categoriaService.findAllCategorias());
        mv.addObject("produtos", produtoService.findAllProdutos());
        return mv;
    }

    @PostMapping(value = "/editar")
    public ModelAndView editarVenda(@Validated Venda venda, Errors errors, Long idEstado, Long idMunicipio) {
        ModelAndView mv = new ModelAndView("venda/vendaEditar");
        boolean erro = false;

        List<String> customMessage = new ArrayList<String>();
        List<Produto> produtos = venda.getProdutos();

        if (!produtos.isEmpty()) {
            customMessage.add("Selecione um Produto.");
            mv.addObject("erroProduto", true);
            erro = true;
        }
        if (errors.hasErrors() || erro) {
            mv.addObject("customMessage", customMessage);
            return mv;
        }
        vendaService.salvarVenda(venda);
        mv.addObject("sucesso", "O venda foi atualizado com sucesso!");
        mv.addObject("venda", venda);
        mv.addObject("estabelecimentos", estabelecimentoService.findAllEstabelecimentos());
        mv.addObject("categorias", categoriaService.findAllCategorias());
        mv.addObject("produtos", produtoService.findAllProdutos());
        return mv;
    }

    @GetMapping("/excluir/{id}")
    public ModelAndView excluirVenda(@PathVariable Long id, RedirectAttributes ra) {
        Venda venda = vendaService.findVendaById(id);
        if (venda != null) {
            vendaService.excluirVendaById(id);
            ra.addFlashAttribute("sucesso", "O Venda foi excluído com sucesso.");
        } else {
            ra.addFlashAttribute("erro", "O Venda não foi encontrado.");
        }
        return new ModelAndView("redirect:/venda/vendas");
    }

    @GetMapping("/visualizar/venda-usuario/{id}")
    public ModelAndView visualizarVendaUsuario(@PathVariable Long id) {
        ModelAndView mv = new ModelAndView("venda/vendaVisualizarUsuario");

        Venda venda = vendaService.findVendaById(id);

        mv.addObject("venda", venda);
        return mv;
    }

    @GetMapping("/visualizar/{id}")
    public ModelAndView visualizarVenda(@PathVariable Long id) {
        ModelAndView mv = new ModelAndView("venda/vendaVisualizar");

        Venda venda = vendaService.findVendaById(id);

        mv.addObject("venda", venda);
        return mv;
    }

}
