package br.ifms.edu.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.ifms.edu.demo.dto.EditoraDTO; 
import br.ifms.edu.demo.service.EditoraService; 
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/editoras")
@Tag(name = "Editora Controller", description = "API para gerenciar editoras")
public class EditoraController {

    private final EditoraService editoraService;

    @Autowired
    public EditoraController(EditoraService editoraService) {
        this.editoraService = editoraService;
    }

    // Listar (GET) todas as editoras
    @Operation(summary = "Lista todas as editoras de forma paginada")
    @ApiResponse(responseCode = "200", description = "Página de editoras retornada")
    @GetMapping
    public ResponseEntity<Page<EditoraDTO>> listarEditoras(Pageable pageable) {
        Page<EditoraDTO> pagina = editoraService.listarEditoras(pageable);
        return ResponseEntity.ok(pagina);
    }
   
    // Buscar por ID (GET)
    @Operation(summary = "Busca uma editora por ID")
    @ApiResponse(responseCode = "200", description = "Editora encontrada")
    @GetMapping("/{id}")
    public ResponseEntity<EditoraDTO> buscarEditoraPorId(@PathVariable Long id) {
        EditoraDTO dto = editoraService.buscarPorId(id);
        return ResponseEntity.ok(dto);
    }

    // Criar (POST)
    @Operation(summary = "Cria uma nova editora")
    @ApiResponse(responseCode = "201", description = "Editora criada com sucesso")
    @PostMapping
    public ResponseEntity<EditoraDTO> salvarEditora(@Valid @RequestBody EditoraDTO dto) {

        EditoraDTO editoraSalva = editoraService.criarEditora(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(editoraSalva);

    }

    // Atualizar (PUT)
    @Operation(summary = "Atualiza uma editora existente")
    @ApiResponse(responseCode = "200", description = "Editora atualizada com sucesso")
    @PutMapping("/{id}")
    public ResponseEntity<EditoraDTO> editarEditora(@PathVariable Long id, @Valid @RequestBody EditoraDTO dto) {
        EditoraDTO editoraAtualizada = editoraService.atualizarEditora(id, dto);
        return ResponseEntity.ok(editoraAtualizada);
    }

    // Deletar (DELETE)
    @Operation(summary = "Deleta uma editora por ID")
    @ApiResponse(responseCode = "204", description = "Editora deletada com sucesso")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarEditora(@PathVariable Long id) {

        editoraService.deletarEditora(id);
        return ResponseEntity.noContent().build();

    }

}