package loja.controller;

import loja.model.Categoria;
import loja.model.Estabelecimento;
import loja.model.ProdutoItem;
import loja.model.Venda;
import loja.service.CategoriaService;
import loja.service.EstabelecimentoService;
import loja.service.ProdutoItemService;
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
    private ProdutoItemService produtoItemService;

    @Autowired
    private EstabelecimentoService estabelecimentoService;

    @GetMapping("/vendas-confirmadas")
    public ModelAndView homeVendasUsuario() {
        ModelAndView mv = new ModelAndView("indexUsuario");
        mv.addObject("categorias", categoriaService.findAllCategorias());
        mv.addObject("vendas", vendaService.findVendasByConfirmado());
        return mv;
    }

    @GetMapping("/vendas-confirmadas/{idCategoria}")
    public ModelAndView filtrarVendasUsuario(@PathVariable Long idCategoria) {
        ModelAndView mv = new ModelAndView("venda/vendasFiltro");
        if (idCategoria != 0) {
            Categoria categoria = categoriaService.findCategoriaById(idCategoria);
            mv.addObject("vendas", vendaService.findVendasByConfirmadoAndCategoria(categoria));
        } else {
            mv.addObject("vendas", vendaService.findVendasByConfirmado());
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

        List<Estabelecimento> estabelecimentos = estabelecimentoService.findAllEstabelecimentos();
        List<Categoria> categorias = categoriaService.findAllCategorias();
        List<ProdutoItem> produtoItens = produtoItemService.findAllProdutoItens();

        addObj(mv);
        mv.addObject("venda", new Venda());
        mv.addObject("estabelecimentos", estabelecimentos);
        mv.addObject("categorias", categorias);
        mv.addObject("produtoItens", produtoItens);
        return mv;
    }

    @PostMapping(value = "/cadastrar")
    public ModelAndView cadastrarVenda(@Validated Venda venda, Errors errors, Long idEstado, Long idMunicipio) {
        ModelAndView mv = new ModelAndView("venda/vendaCadastro");
        addObj(mv);
        boolean erro = false;
        List<String> customMessage = new ArrayList<String>();

        if (venda.getProdutoItensArray() == null) {
            customMessage.add("Selecione um Item de Cardápio.");
            mv.addObject("erroProdutoItens", true);
            erro = true;
        }

        if (errors.hasErrors() || erro) {
            mv.addObject("customMessage", customMessage);
            return mv;
        }
        vendaService.salvarVenda(venda);

        mv.addObject("sucesso", "O Venda foi cadastrado com sucesso.");
        mv.addObject("venda", new Venda());

        return mv;
    }

    @GetMapping("/editar/{id}")
    public ModelAndView editaVenda(@PathVariable Long id) {
        ModelAndView mv = new ModelAndView("venda/vendaEditar");

        Venda venda = vendaService.findVendaById(id);
        List<Estabelecimento> estabelecimentos = estabelecimentoService.findAllEstabelecimentos();
        List<Categoria> categorias = categoriaService.findAllCategorias();
        List<ProdutoItem> produtoItens = produtoItemService.findAllProdutoItens();

        addObj(mv);
        mv.addObject("venda", venda);
        mv.addObject("estabelecimentos", estabelecimentos);
        mv.addObject("categorias", categorias);
        mv.addObject("produtoItens", produtoItens);
        return mv;
    }

    @PostMapping(value = "/editar")
    public ModelAndView editarVenda(@Validated Venda venda, Errors errors, Long idEstado, Long idMunicipio) {
        ModelAndView mv = new ModelAndView("venda/vendaEditar");
        boolean erro = false;
        addObj(mv);

        List<String> customMessage = new ArrayList<String>();
        List<ProdutoItem> produtoItens = venda.getProdutoItens();

        if (!produtoItens.isEmpty()) {
            customMessage.add("Selecione um Item de Cardápio.");
            mv.addObject("erroProdutoItens", true);
            erro = true;
        }
        if (errors.hasErrors() || erro) {
            mv.addObject("customMessage", customMessage);
            return mv;
        }
        vendaService.salvarVenda(venda);
        mv.addObject("sucesso", "O venda foi atualizado com sucesso!");
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

    private void addObj(ModelAndView mv) {
        mv.addObject("vendas", vendaService.findAllVendas());
        mv.addObject("categorias", categoriaService.findAllCategorias());
    }

}
