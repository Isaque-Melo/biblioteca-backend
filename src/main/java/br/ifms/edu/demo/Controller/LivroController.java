package br.ifms.edu.demo.Controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.ifms.edu.demo.dto.LivroFormDTO;
import br.ifms.edu.demo.dto.LivroResponseDTO;
import br.ifms.edu.demo.model.Autor;
import br.ifms.edu.demo.model.Editora;
import br.ifms.edu.demo.model.Livro;
import br.ifms.edu.demo.repository.AutorRepository;
import br.ifms.edu.demo.repository.EditoraRepository;
import br.ifms.edu.demo.repository.LivroRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/api/livros")
public class LivroController {

    @Autowired
    private LivroRepository livroRepository;
    @Autowired
    private AutorRepository autorRepository;
    @Autowired
    private EditoraRepository editoraRepository;

    // LISTAR (GET)
    @Operation(summary = "Lista todos os livros")
    @ApiResponse(responseCode = "200", description = "Lista de livros retornada com sucesso")
    @GetMapping
    public List<LivroResponseDTO> listarLivros() {
        return livroRepository.findAll().stream()
                .map(this::toResponseDTO)
                .toList();
    }
    
    // BUSCAR POR ID (GET)
    @Operation(summary = "Busca um livro por ID")
    @ApiResponse(responseCode = "200", description = "Livro encontrado")
    @ApiResponse(responseCode = "404", description = "Livro não encontrado")
    @GetMapping("/{id}")
    public ResponseEntity<LivroResponseDTO> buscarLivroPorId(@PathVariable Long id) {
        return livroRepository.findById(id)
                .map(livro -> ResponseEntity.ok(toResponseDTO(livro)))
                .orElse(ResponseEntity.notFound().build());
    }

    // CRIAR (POST) 
    @Operation(summary = "Cria um novo livro")
    @ApiResponse(responseCode = "201", description = "Livro criado com sucesso")
    @ApiResponse(responseCode = "400", description = "Dados inválidos")
    @PostMapping
    public ResponseEntity<LivroResponseDTO> salvarLivro(@RequestBody LivroFormDTO dto) {
        Livro livro = toEntity(dto);
        Livro novoLivro = livroRepository.save(livro);
        return ResponseEntity.status(HttpStatus.CREATED).body(toResponseDTO(novoLivro));
    }
    
    // ATUALIZAR (PUT) 
    @Operation(summary = "Atualiza um livro existente")
    @ApiResponse(responseCode = "200", description = "Livro atualizado com sucesso")
    @PutMapping("/{id}")
    public ResponseEntity<LivroResponseDTO> editarLivro(@PathVariable Long id, @RequestBody LivroFormDTO dto) {
        return livroRepository.findById(id)
                .map(livroExistente -> {
                    Livro livroAtualizado = toEntity(dto, id);
                    livroAtualizado = livroRepository.save(livroAtualizado);
                    return ResponseEntity.ok(toResponseDTO(livroAtualizado));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETAR (DELETE)
    @Operation(summary = "Deleta um livro por ID")
    @ApiResponse(responseCode = "204", description = "Livro deletado com sucesso")
    @ApiResponse(responseCode = "404", description = "Livro não encontrado")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarLivro(@PathVariable Long id) {
        return livroRepository.findById(id)
                .map(livro -> {
                    livroRepository.deleteById(id);
                    return ResponseEntity.noContent().build(); 
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // --- MAPPERS ---

    // Converte Entidade para DTO de Resposta (para enviar ao Angular)
    private LivroResponseDTO toResponseDTO(Livro livro) {
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

    // Converte DTO de Formulário para Entidade (para salvar no banco)
    private Livro toEntity(LivroFormDTO dto) {
        return toEntity(dto, null); // Chama o método de update com id nulo
    }
    
    private Livro toEntity(LivroFormDTO dto, Long id) {
        Livro livro = new Livro();
        if (id != null) {
            livro.setId(id); // Para updates
        }
        livro.setTitulo(dto.getTitulo());
        livro.setAnoPublicacao(dto.getAnoPublicacao());

        // Busca a Editora real no banco
        if (dto.getEditoraId() != null) {
            Editora editora = editoraRepository.findById(dto.getEditoraId())
                    .orElseThrow(() -> new RuntimeException("Editora com ID " + dto.getEditoraId() + " não encontrada."));
            livro.setEditora(editora);
        }

    
        if (dto.getAutoresIds() != null && !dto.getAutoresIds().isEmpty()) {
            List<Autor> autores = autorRepository.findAllById(dto.getAutoresIds());
            livro.setAutores(autores);
        }

        return livro;
    }
}