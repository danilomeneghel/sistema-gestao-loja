package loja.controller;

import loja.model.ProdutoItem;
import loja.service.CategoriaService;
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
import loja.model.Produto;
import loja.service.ProdutoItemService;
import loja.service.ProdutoService;

@Controller
@RequestMapping("/produto")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private ProdutoItemService produtoItemService;

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping("/produtos")
    public ModelAndView mostrarProdutos() {
        ModelAndView mv = new ModelAndView("produto/produtos");
        mv.addObject("produtos", produtoService.findAllProdutos());
        return mv;
    }

    @GetMapping("/cadastro")
    public ModelAndView cadastroProduto() {
        ModelAndView mv = new ModelAndView("produto/produtoCadastro");
        mv.addObject("produto", new Produto());
        return mv;
    }

    @PostMapping("/cadastrar")
    public ModelAndView cadastrarProduto(@Validated Produto produto, Errors errors) {
        ModelAndView mv = new ModelAndView("produto/produtoCadastro");
        if (errors.hasErrors()) {
            return mv;
        }
        mv.addObject("sucesso", "O Produto foi cadastrado com sucesso!");
        produtoService.salvarProduto(produto);
        mv.addObject("produto", new Produto());
        return mv;
    }

    @GetMapping("/editar/{id}")
    public ModelAndView editaProduto(@PathVariable Long id) {
        ModelAndView mv = new ModelAndView("produto/produtoEditar");
        mv.addObject("produto", produtoService.findProdutoById(id));
        return mv;
    }

    @PostMapping("/editar")
    public ModelAndView editarProduto(@Validated Produto produto, Errors errors) {
        ModelAndView mv = new ModelAndView("produto/produtoEditar");
        if (errors.hasErrors()) {
            return mv;
        }
        mv.addObject("sucesso", "O Produto foi atualizado com sucesso!");
        produtoService.salvarProduto(produto);
        return mv;
    }

    @GetMapping("/excluir/{id}")
    public ModelAndView excluirProduto(@PathVariable Long id, RedirectAttributes ra) {
        Produto produto = produtoService.findProdutoById(id);
        if (produto != null) {
            produtoService.excluirProduto(id);
            ra.addFlashAttribute("sucesso", "O Produto foi excluído com sucesso.");
        } else {
            ra.addFlashAttribute("erro", "O Produto não foi encontrado.");
        }
        return new ModelAndView("redirect:/produto/produtos");
    }

    @GetMapping("/produto-itens")
    public ModelAndView mostrarprodutoItens() {
        ModelAndView mv = new ModelAndView("produto-item/produtoItens");
        mv.addObject("produtoItens", produtoItemService.findAllProdutoItens());
        return mv;
    }

    @GetMapping("/produto-item/cadastro")
    public ModelAndView cadastroprodutoItem() {
        ModelAndView mv = new ModelAndView("produto-item/produtoItemCadastro");
        mv.addObject("produtoItem", new ProdutoItem());
        mv.addObject("categorias", categoriaService.findAllCategorias());
        mv.addObject("produtos", produtoService.findAllProdutos());
        return mv;
    }

    @PostMapping(value = "/produto-item/cadastrar", consumes = "multipart/form-data")
    public ModelAndView cadastrarprodutoItem(@Validated ProdutoItem produtoItem, Errors errors) {
        ModelAndView mv = new ModelAndView("produto-item/produtoItemCadastro");
        produtoItemService.salvarProdutoItem(produtoItem);
        if (errors.hasErrors()) {
            return mv;
        }
        mv.addObject("produtoItem", new ProdutoItem());
        mv.addObject("sucesso", "O Item do Cardápio foi cadastrado com sucesso!");
        return mv;
    }

    @GetMapping("/produto-item/editar/{id}")
    public ModelAndView editaprodutoItem(@PathVariable Long id) {
        ModelAndView mv = new ModelAndView("produto-item/produtoItemEditar");
        mv.addObject("produtoItem", produtoItemService.findProdutoItemById(id));
        mv.addObject("categorias", categoriaService.findAllCategorias());
        mv.addObject("produtos", produtoService.findAllProdutos());
        return mv;
    }

    @PostMapping(value = "/produto-item/editar", consumes = "multipart/form-data")
    public ModelAndView editarprodutoItem(@Validated ProdutoItem produtoItem, Errors errors) {
        ModelAndView mv = new ModelAndView("produto-item/produtoItemEditar");
        if (errors.hasErrors()) {
            return mv;
        }
        mv.addObject("sucesso", "O Item Do Cardápio foi atualizado com sucesso!");
        produtoItem.setImagens(produtoItemService.findProdutoItemById(produtoItem.getId()).getImagens());
        produtoItemService.salvarProdutoItem(produtoItem);
        return mv;
    }

    @GetMapping("/produto-item/excluir/{id}")
    public ModelAndView excluirprodutoItem(@PathVariable Long id, RedirectAttributes ra) {
        ProdutoItem produtoItem = produtoItemService.findProdutoItemById(id);
        if (produtoItem != null) {
            produtoItemService.excluirProdutoItem(id);
            ra.addFlashAttribute("sucesso", "O Item do Cardápio foi excluído com sucesso.");
        } else {
            ra.addFlashAttribute("erro", "O Item do Cardápio não foi encontrado.");
        }
        return new ModelAndView("redirect:/produto-item/produtoItens");
    }

    @GetMapping("/produto-item/visualizar/{id}")
    public ModelAndView visualizarprodutoItem(@PathVariable Long id) {
        ModelAndView mv = new ModelAndView("produto-item/produtoItemVisualizar");
        ProdutoItem produtoItem = produtoItemService.findProdutoItemById(id);
        mv.addObject("produtoItem", produtoItem);
        return mv;
    }

}
