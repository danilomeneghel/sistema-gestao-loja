package loja.controller;

import loja.model.Categoria;
import loja.model.Fornecedor;
import loja.model.Produto;
import loja.service.CategoriaService;
import loja.service.FornecedorService;
import loja.service.ProdutoService;
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

import java.util.List;

@Controller
@RequestMapping("/produto")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private CategoriaService categoriaService;

    @Autowired
    private FornecedorService fornecedorService;

    @GetMapping("/produtos")
    public ModelAndView mostrarProdutos() {
        ModelAndView mv = new ModelAndView("produto/produtos");
        mv.addObject("produtos", produtoService.findAllProdutos());
        return mv;
    }

    @GetMapping("/cadastro")
    public ModelAndView cadastroProduto() {
        ModelAndView mv = new ModelAndView("produto/produtoCadastro");
        List<Categoria> categorias = categoriaService.findAllCategorias();
        List<Fornecedor> fornecedores = fornecedorService.findAllFornecedores();
        mv.addObject("categorias", categorias);
        mv.addObject("fornecedores", fornecedores);
        mv.addObject("produto", new Produto());
        return mv;
    }

    @PostMapping("/cadastrar")
    public ModelAndView cadastrarProduto(@Validated Produto produto, Errors errors) {
        ModelAndView mv = new ModelAndView("produto/produtoCadastro");
        List<Categoria> categorias = categoriaService.findAllCategorias();
        List<Fornecedor> fornecedores = fornecedorService.findAllFornecedores();
        mv.addObject("categorias", categorias);
        mv.addObject("fornecedores", fornecedores);
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
        Produto produto = produtoService.findProdutoById(id);
        List<Categoria> categorias = categoriaService.findAllCategorias();
        List<Fornecedor> fornecedores = fornecedorService.findAllFornecedores();
        mv.addObject("categorias", categorias);
        mv.addObject("fornecedores", fornecedores);
        mv.addObject("idFornecedor", produto.getFornecedor().getId());
        mv.addObject("produto", produto);
        return mv;
    }

    @PostMapping("/editar")
    public ModelAndView editarProduto(@Validated Produto produto, Errors errors) {
        ModelAndView mv = new ModelAndView("produto/produtoEditar");
        List<Categoria> categorias = categoriaService.findAllCategorias();
        List<Fornecedor> fornecedores = fornecedorService.findAllFornecedores();
        mv.addObject("categorias", categorias);
        mv.addObject("fornecedores", fornecedores);
        mv.addObject("idFornecedor", produto.getFornecedor().getId());
        mv.addObject("produto", produto);
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

    @GetMapping("/visualizar/produto-usuario/{id}")
    public ModelAndView visualizarProdutoUsuario(@PathVariable Long id) {
        ModelAndView mv = new ModelAndView("produto/produtoVisualizarUsuario");

        Produto produto = produtoService.findProdutoById(id);

        mv.addObject("produto", produto);
        return mv;
    }

    @GetMapping("/visualizar/{id}")
    public ModelAndView visualizarProduto(@PathVariable Long id) {
        ModelAndView mv = new ModelAndView("produto/produtoVisualizar");

        Produto produto = produtoService.findProdutoById(id);

        mv.addObject("produto", produto);
        return mv;
    }

}
