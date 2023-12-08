package loja.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import loja.entity.CategoriaEntity;
import loja.model.Categoria;
import loja.repository.CategoriaRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    private ModelMapper modelMapper = new ModelMapper();

    public List<Categoria> findAllCategorias() {
        List<CategoriaEntity> categorias = categoriaRepository.findAll();
        return categorias.stream().map(entity -> modelMapper.map(entity, Categoria.class)).collect(Collectors.toList());
    }

    public Categoria findCategoriaById(Long id) {
        Optional<CategoriaEntity> categoriaEntity = categoriaRepository.findById(id);
        if (!categoriaEntity.isEmpty()) {
            return modelMapper.map(categoriaEntity.get(), Categoria.class);
        }
        return null;
    }

    public Categoria salvarCategoria(Categoria categoria) {
        CategoriaEntity categoriaEntity = modelMapper.map(categoria, CategoriaEntity.class);
        CategoriaEntity salvarCategoria = categoriaRepository.save(categoriaEntity);
        return modelMapper.map(salvarCategoria, Categoria.class);
    }

    public void excluirCategoria(Long id) {
        categoriaRepository.deleteById(id);
    }

    public List<Categoria> findCategoriaByNome(String nome) {
        List<CategoriaEntity> categorias = categoriaRepository.findByNomeContainingIgnoreCase(nome);
        return categorias.stream().map(entity -> modelMapper.map(entity, Categoria.class)).collect(Collectors.toList());
    }

}