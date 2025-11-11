// Em: src/main/java/br/ifms/edu/demo/mapper/LivroMapper.java
package br.ifms.edu.demo.mapper;

import org.springframework.stereotype.Component;
import br.ifms.edu.demo.model.Autor;
import br.ifms.edu.demo.model.Editora;
import br.ifms.edu.demo.model.Livro;
import br.ifms.edu.demo.dto.LivroFormDTO;
import br.ifms.edu.demo.dto.LivroResponseDTO;
import br.ifms.edu.demo.repository.AutorRepository;
import br.ifms.edu.demo.repository.EditoraRepository;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class LivroMapper {

    private final AutorRepository autorRepository;
    private final EditoraRepository editoraRepository;

    public LivroMapper(AutorRepository autorRepository, EditoraRepository editoraRepository) {
        this.autorRepository = autorRepository;
        this.editoraRepository = editoraRepository;
    }

    public LivroResponseDTO toResponseDTO(Livro livro) {
        String nomeEditora = (livro.getEditora() != null) ? livro.getEditora().getNome() : null;
        
        List<String> nomesAutores = livro.getAutores().stream()
                .map(Autor::getNome)
                .collect(Collectors.toList());

        return new LivroResponseDTO(
            livro.getId(), 
            livro.getTitulo(), 
            livro.getAnoPublicacao(), 
            nomeEditora, 
            nomesAutores
        );
    }

    // Converte DTO de Formulário para Entidade (o que estava no seu controller)
    public Livro toEntity(LivroFormDTO dto) {
        Livro livro = new Livro();
        livro.setTitulo(dto.getTitulo());
        livro.setAnoPublicacao(dto.getAnoPublicacao());

        // Busca a Editora real no banco
        Editora editora = editoraRepository.findById(dto.getEditoraId())
                .orElseThrow(() -> new RuntimeException("Editora com ID " + dto.getEditoraId() + " não encontrada."));
        livro.setEditora(editora);

        // Busca os Autores reais no banco
        List<Autor> autores = autorRepository.findAllById(dto.getAutoresIds());
        livro.setAutores(autores);

        return livro;
    }
}