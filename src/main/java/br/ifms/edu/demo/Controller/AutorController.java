package br.ifms.edu.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page; // Importar Page
import org.springframework.data.domain.Pageable; // Importar Pageable
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.ifms.edu.demo.dto.AutorDTO;
import br.ifms.edu.demo.service.AutorService; // Importar o Serviço
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/autores")
@Tag(name = "Autor Controller", description = "API para gerenciar autores")
public class AutorController {

    private final AutorService autorService;

    @Autowired
    public AutorController(AutorService autorService) {
        this.autorService = autorService;
    }

    // LISTAR (GET) 
    @Operation(summary = "Lista todos os autores de forma paginada")
    @ApiResponse(responseCode = "200", description = "Página de autores retornada")
    @GetMapping
    public ResponseEntity<Page<AutorDTO>> listarAutores(Pageable pageable) {
        Page<AutorDTO> pagina = autorService.listarAutores(pageable);
        return ResponseEntity.ok(pagina);
    }
   
    // BUSCAR POR ID (GET)
    @Operation(summary = "Busca um autor por ID")
    @ApiResponse(responseCode = "200", description = "Autor encontrado")
    @GetMapping("/{id}")
    public ResponseEntity<AutorDTO> buscarAutorPorId(@PathVariable Long id) {
        AutorDTO dto = autorService.buscarPorId(id);
        return ResponseEntity.ok(dto);
        // (No futuro, o service lançará uma exceção que um @ControllerAdvice pegará)
    }

    // CRIAR (POST)
    @Operation(summary = "Cria um novo autor")
    @ApiResponse(responseCode = "201", description = "Autor criado com sucesso")
    @PostMapping
    public ResponseEntity<AutorDTO> salvarAutor(@Valid @RequestBody AutorDTO dto) {
        AutorDTO autorSalvo = autorService.criarAutor(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(autorSalvo);
    }
    
    // ATUALIZAR (PUT)
    @Operation(summary = "Atualiza um autor existente")
    @ApiResponse(responseCode = "200", description = "Autor atualizado com sucesso")
    @PutMapping("/{id}")
    public ResponseEntity<AutorDTO> editarAutor(@PathVariable Long id, @Valid @RequestBody AutorDTO dto) {
        AutorDTO autorAtualizado = autorService.atualizarAutor(id, dto);
        return ResponseEntity.ok(autorAtualizado);
    }

    // DELETAR (DELETE)
    @Operation(summary = "Deleta um autor por ID")
    @ApiResponse(responseCode = "204", description = "Autor deletado com sucesso")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarAutor(@PathVariable Long id) {
        autorService.deletarAutor(id);
        return ResponseEntity.noContent().build();
    }

}