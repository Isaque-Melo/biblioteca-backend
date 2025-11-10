package br.ifms.edu.demo.Controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import br.ifms.edu.demo.dto.AutorDTO; // Importe o DTO
import br.ifms.edu.demo.model.Autor;
import br.ifms.edu.demo.model.Livro;
import br.ifms.edu.demo.repository.AutorRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/autores")
@CrossOrigin(origins = "http://localhost:4200")
@Tag(name = "Autor Controller", description = "API para gerenciar autores")
public class AutorController {

    @Autowired
    private AutorRepository autorRepository;

    // LISTAR (GET)
    @Operation(summary = "Lista todos os autores")
    @ApiResponse(responseCode = "200", description = "Lista de autores retornada com sucesso")
    @GetMapping
    public List<AutorDTO> listarAutores() {
        return autorRepository.findAll().stream()
                .map(this::toDTO) 
                .toList();
    }
   
    // BUSCAR POR ID (GET)
    @Operation(summary = "Busca um autor por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Autor encontrado"),
        @ApiResponse(responseCode = "404", description = "Autor não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<AutorDTO> buscarAutorPorId(@PathVariable Long id) {
        return autorRepository.findById(id)
                .map(autor -> ResponseEntity.ok(toDTO(autor))) // Converte para DTO
                .orElse(ResponseEntity.notFound().build());
    }

    // CRIAR (POST)
    @Operation(summary = "Cria um novo autor")
    @ApiResponse(responseCode = "201", description = "Autor criado com sucesso")
    @ApiResponse(responseCode = "400", description = "Dados inválidos ")
    @PostMapping
    public ResponseEntity<AutorDTO> salvarAutor(@RequestBody AutorDTO dto) {
        Autor autor = toEntity(dto); // Converte DTO para Entidade
        Autor novoAutor = autorRepository.save(autor);
        return ResponseEntity.status(HttpStatus.CREATED).body(toDTO(novoAutor));
    }
    
    // ATUALIZAR (PUT)
    @Operation(summary = "Atualiza um autor existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Autor atualizado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Autor não encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<AutorDTO> editarAutor(@PathVariable Long id, @RequestBody AutorDTO dto) {
        return autorRepository.findById(id)
                .map(autorExistente -> {
                    Autor autorAtualizado = toEntity(dto, id); 
                    autorAtualizado = autorRepository.save(autorAtualizado);
                    return ResponseEntity.ok(toDTO(autorAtualizado));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETAR (DELETE)
    @Operation(summary = "Deleta um autor por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Autor deletado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Autor não encontrado")
    })
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> deletarAutor(@PathVariable Long id) {
        return autorRepository.findById(id)
                .map(autor -> {
                    // dissociar o autor dos livros antes de deletar
                    for (Livro livro : autor.getLivros()) {
                        livro.getAutores().remove(autor);
                    }
                    // agora deletar o autor
                    autorRepository.deleteById(id);
                    return ResponseEntity.noContent().build(); 
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // --- MAPPERS ---
    private AutorDTO toDTO(Autor autor) {
        return new AutorDTO(autor.getId(), autor.getNome(), autor.getNacionalidade());
    }

    private Autor toEntity(AutorDTO dto) {
        Autor autor = new Autor();
        autor.setNome(dto.nome());
        autor.setNacionalidade(dto.nacionalidade());
        return autor;
    }
    
    private Autor toEntity(AutorDTO dto, Long id) {
        Autor autor = new Autor();
        autor.setId(id); 
        autor.setNome(dto.nome());
        autor.setNacionalidade(dto.nacionalidade());
        return autor;
    }
}