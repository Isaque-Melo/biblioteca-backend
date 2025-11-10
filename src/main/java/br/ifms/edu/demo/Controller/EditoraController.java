package br.ifms.edu.demo.Controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.ifms.edu.demo.dto.EditoraDTO; 
import br.ifms.edu.demo.model.Editora;
import br.ifms.edu.demo.repository.EditoraRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/editoras")
@Tag(name = "Editora Controller", description = "API para gerenciar editoras")
public class EditoraController {

    @Autowired
    private EditoraRepository editoraRepository;

    // Listar (GET) todas as editoras
    @Operation(summary = "Lista todas as editoras")
    @ApiResponse(responseCode = "200", description = "Lista de editoras retornada com sucesso")
    @GetMapping
    public List<EditoraDTO> listarEditoras() {
        return editoraRepository.findAll().stream()
                .map(this::toDTO)
                .toList();
    }
   

    // Buscar por ID (GET)
    @Operation(summary = "Busca uma editora por ID")
    @ApiResponse(responseCode = "200", description = "Editora encontrada")
    @ApiResponse(responseCode = "404", description = "Editora não encontrada")
    @GetMapping("/{id}")
    public ResponseEntity<EditoraDTO> buscarEditoraPorId(@PathVariable Long id) {
        return editoraRepository.findById(id)
                .map(editora -> ResponseEntity.ok(toDTO(editora)))
                .orElse(ResponseEntity.notFound().build());
    }

    // Criar (POST)
    @Operation(summary = "Cria uma nova editora")
    @ApiResponse(responseCode = "201", description = "Editora criada com sucesso")
    @ApiResponse(responseCode = "400", description = "Dados inválidos")
    @PostMapping
    public ResponseEntity<EditoraDTO> salvarEditora( @Valid @RequestBody EditoraDTO dto) {
        Editora editora = toEntity(dto);
        Editora novaEditora = editoraRepository.save(editora);
        return ResponseEntity.status(HttpStatus.CREATED).body(toDTO(novaEditora));
    }

    // Atualizar (PUT)
    @Operation(summary = "Atualiza uma editora existente")
    @ApiResponse(responseCode = "200", description = "Editora atualizada com sucesso")
    @ApiResponse(responseCode = "404", description = "Editora não encontrada")
    @PutMapping("/{id}")
    public ResponseEntity<EditoraDTO> editarEditora(@PathVariable Long id, @Valid @RequestBody EditoraDTO dto) {
        return editoraRepository.findById(id)
                .map(editoraExistente -> {
                    Editora editoraAtualizada = toEntity(dto, id);
                    editoraAtualizada = editoraRepository.save(editoraAtualizada);
                    return ResponseEntity.ok(toDTO(editoraAtualizada));
                })
                .orElse(ResponseEntity.notFound().build());
    }
    // Deletar (DELETE)
    @Operation(summary = "Deleta uma editora por ID")
    @ApiResponse(responseCode = "204", description = "Editora deletada com sucesso")
    @ApiResponse(responseCode = "404", description = "Editora não encontrada")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarEditora(@PathVariable Long id) {
        return editoraRepository.findById(id)
                .map(editora -> {
                    editoraRepository.deleteById(id);
                    return ResponseEntity.noContent().build(); 
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // --- MAPPERS ---
    private EditoraDTO toDTO(Editora editora) {
        return new EditoraDTO(editora.getId(), editora.getNome(), editora.getCidade());
    }

    private Editora toEntity(EditoraDTO dto) {
        Editora editora = new Editora();
        editora.setNome(dto.nome());
        editora.setCidade(dto.cidade());
        return editora;
    }
    
    private Editora toEntity(EditoraDTO dto, Long id) {
        Editora editora = new Editora();
        editora.setId(id);
        editora.setNome(dto.nome());
        editora.setCidade(dto.cidade());
        return editora;
    }
}