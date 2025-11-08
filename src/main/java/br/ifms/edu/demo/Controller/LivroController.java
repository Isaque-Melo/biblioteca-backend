package br.ifms.edu.demo.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import br.ifms.edu.demo.model.Livro;
import br.ifms.edu.demo.repository.LivroRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/livros")
@Tag(name = "Livro Controller", description = "API para gerenciar livros")
public class LivroController {

    @Autowired
    private LivroRepository livroRepository;

    // READ - Listar todos os livros
    @GetMapping
    public List<Livro> listarLivros() {
        return livroRepository.findAll();
    }

    //create - Mostrar formulário para adicionar novo livro
    @PostMapping("/livros/novo")
    public ResponseEntity<Livro> mostrarFormularioDeAdicionarLivro(@RequestBody Livro livro) {
        Livro novoLivro = new Livro();
        return ResponseEntity.ok(novoLivro);
    }
       
     
    // UPDATE - Mostrar formulário para editar livro
    @PutMapping("/livros/editar/{id}")
    public ResponseEntity<Livro> mostrarFormularioDeEditarLivro(@PathVariable Long id, @RequestBody Livro livroDetalhes) {
        // Busca o livro no banco pelo ID ou lança exceção se não encontrar
        Livro livro = livroRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID do livro inválido:" + id));
        return ResponseEntity.ok(livro);
    }

    // DELETE - Deletar um livro
    @DeleteMapping("/livros/deletar/{id}")
    public ResponseEntity<Void> deletarLivro(@PathVariable Long id) {
        livroRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }




}
