package br.ifms.edu.demo.Controller;

import br.ifms.edu.demo.model.Editora;
import br.ifms.edu.demo.repository.EditoraRepository;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/api/editoras")
@Tag(name = "Editora Controller", description = "API para gerenciar editoras")
public class EditoraController {

    @Autowired
    private EditoraRepository editoraRepository;

    // READ 
    @GetMapping("/editoras")
    public List<Editora> listarEditoras() {
        return editoraRepository.findAll();
    }
    // READ (Por ID) - Retorna os DADOS (JSON) de uma editora
    @GetMapping("/{id}")
    public ResponseEntity<Editora> buscarEditoraPorId(@PathVariable Long id) {
        // Encontra a editora ou retorna 404 (Não Encontrado)
        return editoraRepository.findById(id)
                .map(editora -> ResponseEntity.ok(editora))
                .orElse(ResponseEntity.notFound().build());
    }

    // CREATE - Mostrar formulário para adicionar nova editora
     @PostMapping
    public ResponseEntity<Editora> salvarEditora(@RequestBody Editora editora) {
        // @RequestBody converte o JSON enviado no corpo da requisição para o objeto Editora
        Editora novaEditora = editoraRepository.save(editora);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaEditora); // <-- DEPOIS: Retorna 201 Created com o objeto salvo
    }

    // UPDATE - Mostrar formulário para editar editora
   @PutMapping("/{id}")
    public ResponseEntity<Editora> editarEditora(@PathVariable Long id, @RequestBody Editora editoraDetalhes) {
        return editoraRepository.findById(id)
                .map(editoraExistente -> {
                    editoraExistente.setNome(editoraDetalhes.getNome());
                    editoraExistente.setCidade(editoraDetalhes.getCidade());
                    Editora editoraAtualizada = editoraRepository.save(editoraExistente);
                    return ResponseEntity.ok(editoraAtualizada);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE - Deletar editora
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarEditora(@PathVariable Long id) {
        return editoraRepository.findById(id)
                .map(editora -> {
                    editoraRepository.delete(editora);
                    return ResponseEntity.ok().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
