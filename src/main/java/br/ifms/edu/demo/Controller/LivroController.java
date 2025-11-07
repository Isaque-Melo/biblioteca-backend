package br.ifms.edu.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import br.ifms.edu.demo.model.Livro;
import br.ifms.edu.demo.repository.AutorRepository;
import br.ifms.edu.demo.repository.EditoraRepository;
import br.ifms.edu.demo.repository.LivroRepository;

@Controller
public class LivroController {

    @Autowired
    private LivroRepository livroRepository;

     @Autowired
    private EditoraRepository editoraRepository;

    @Autowired
    private AutorRepository autorRepository;



    @GetMapping("/")
    public String home() {
        return "redirect:/livros"; 
    }

    @GetMapping("/livros")
    public String listarLivros(Model model) {
        model.addAttribute("livros", livroRepository.findAll());
        return "lista-livros"; 
    }

      @GetMapping("/livros/novo")
    public String mostrarFormularioDeAdicionarLivro(Model model) {
        model.addAttribute("livro", new Livro()); // Objeto livro em branco para o formulário
        model.addAttribute("listaEditoras", editoraRepository.findAll());
        model.addAttribute("listaAutores", autorRepository.findAll());
        return "form-livro"; 
    }

     @PostMapping("/livros/salvar")
    public String salvarLivro(@ModelAttribute("livro") Livro livro) {
        livroRepository.save(livro);
        return "redirect:/livros"; 
    }

    @GetMapping("/livros/editar/{id}")
    public String mostrarFormularioDeEditarLivro(@PathVariable Long id, Model model) {
        // Busca o livro no banco pelo ID ou lança exceção se não encontrar
        Livro livro = livroRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID do livro inválido:" + id));
        
        model.addAttribute("livro", livro);
        model.addAttribute("listaEditoras", editoraRepository.findAll());
        model.addAttribute("listaAutores", autorRepository.findAll());
        return "form-livro";
    }

    // DELETE - Deletar um livro
    @GetMapping("/livros/deletar/{id}")
    public String deletarLivro(@PathVariable Long id) {
        livroRepository.deleteById(id);
        return "redirect:/livros"; 
    }




}
