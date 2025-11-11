package br.ifms.edu.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.ifms.edu.demo.dto.LivroFormDTO;
import br.ifms.edu.demo.dto.LivroResponseDTO;
import br.ifms.edu.demo.service.LivroService; // Única dependência de lógica
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/livros")
public class LivroController {

    private final LivroService livroService;

    @Autowired
    public LivroController(LivroService livroService) {
        this.livroService = livroService;
    }

    // LISTAR (GET)
    @Operation(summary = "Lista todos os livros de forma paginada")
    @ApiResponse(responseCode = "200", description = "Página de livros retornada")
    @GetMapping
    public ResponseEntity<Page<LivroResponseDTO>> listarLivros(Pageable pageable) {
        Page<LivroResponseDTO> pagina = livroService.listarLivros(pageable);
        return ResponseEntity.ok(pagina);
    }
    
    // BUSCAR POR ID (GET)
    @Operation(summary = "Busca um livro por ID")
    @ApiResponse(responseCode = "200", description = "Livro encontrado")
    @ApiResponse(responseCode = "404", description = "Livro não encontrado")
    @GetMapping("/{id}")
    public ResponseEntity<LivroResponseDTO> buscarLivroPorId(@PathVariable Long id) {
        LivroResponseDTO dto = livroService.buscarPorId(id);
        return ResponseEntity.ok(dto);
    }

    // CRIAR (POST) 
    @Operation(summary = "Cria um novo livro")
    @ApiResponse(responseCode = "201", description = "Livro criado com sucesso")
    @PostMapping
    public ResponseEntity<LivroResponseDTO> salvarLivro(@Valid @RequestBody LivroFormDTO dto) {
        LivroResponseDTO novoLivro = livroService.criarLivro(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoLivro);
    }
    
    // ATUALIZAR (PUT) 
    @Operation(summary = "Atualiza um livro existente")
    @ApiResponse(responseCode = "200", description = "Livro atualizado com sucesso")
    @PutMapping("/{id}")
    public ResponseEntity<LivroResponseDTO> editarLivro(@PathVariable Long id, @Valid @RequestBody LivroFormDTO dto) {
        LivroResponseDTO livroAtualizado = livroService.atualizarLivro(id, dto);
        return ResponseEntity.ok(livroAtualizado);
    }

    // DELETAR (DELETE)
    @Operation(summary = "Deleta um livro por ID")
    @ApiResponse(responseCode = "204", description = "Livro deletado com sucesso")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarLivro(@PathVariable Long id) {
        livroService.deletarLivro(id);
        return ResponseEntity.noContent().build();
    }
}