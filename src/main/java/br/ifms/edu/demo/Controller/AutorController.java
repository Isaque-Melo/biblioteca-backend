package br.ifms.edu.demo.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import br.ifms.edu.demo.repository.AutorRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import br.ifms.edu.demo.model.Autor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/autores")
@Tag(name = "Autor Controller", description = "API para gerenciar autores")
@CrossOrigin(origins = "http://localhost:4200")
public class AutorController {

    @Autowired
    private AutorRepository autorRepository;

    @GetMapping
    public List<Autor> listarAutores() {
        return autorRepository.findAll();
    }
   // READ (Por ID) 
    @GetMapping("/{id}")
    public ResponseEntity<Autor> buscarAutorPorId(@PathVariable Long id) {
        // Encontra o autor ou retorna 404 (Não Encontrado)
        return autorRepository.findById(id)
                .map(autor -> ResponseEntity.ok(autor))
                .orElse(ResponseEntity.notFound().build());
    }

    // CREATE 
    @PostMapping
    public ResponseEntity<Autor> salvarAutor(@RequestBody Autor autor) {
        Autor novoAutor = autorRepository.save(autor);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoAutor); // Retorna 201 Created com o objeto salvo
    }
    
    // UPDATE 
    @PutMapping("/{id}")
    public ResponseEntity<Autor> editarAutor(@PathVariable Long id, @RequestBody Autor autorDetalhes) {
        return autorRepository.findById(id)
                .map(autorExistente -> {
                    autorExistente.setNome(autorDetalhes.getNome());
                    autorExistente.setNacionalidade(autorDetalhes.getNacionalidade());
                    Autor autorAtualizado = autorRepository.save(autorExistente);
                    return ResponseEntity.ok(autorAtualizado);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE 
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarAutor(@PathVariable Long id) {
        return autorRepository.findById(id)
                .map(autor -> {
                    autorRepository.deleteById(id);
                    return ResponseEntity.noContent().build(); 
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
