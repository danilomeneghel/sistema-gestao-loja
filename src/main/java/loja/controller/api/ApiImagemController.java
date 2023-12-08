package loja.controller.api;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import loja.service.ImagemService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@RestController
@RequestMapping("/api/imagem")
@Tag(name = "Imagem")
@Validated
public class ApiImagemController {

    @Autowired
    private ImagemService imagemService;

    @GetMapping("/{nomeArquivo:.+}")
    public ResponseEntity<Resource> carregarImagem(@PathVariable String nomeArquivo, RedirectAttributes ra) {
        Resource resource = imagemService.buscarArquivo(nomeArquivo);
        String contentType = null;
        try {
            Path path = new File(resource.getFile().getAbsolutePath()).toPath();
            contentType = Files.probeContentType(path);
        } catch (IOException ex) {
            ra.addFlashAttribute("erro", "A Imagem não foi encontrada.");
        }
        if (contentType == null) {
            contentType = "application/octet-stream";
        }
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .body(resource);
    }

    @PostMapping("/armazenar-imagem")
    public ResponseEntity<String> armazenarImagem(@PathVariable Long idProdutoItem, @RequestBody MultipartFile[] arquivos) {
        return new ResponseEntity<>(imagemService.armazenarImagem(idProdutoItem, arquivos), HttpStatus.CREATED);
    }

    @DeleteMapping("/excluir/{id}")
    public void excluirImagem(@PathVariable Long id) {
        imagemService.excluirImagem(id);
    }

}