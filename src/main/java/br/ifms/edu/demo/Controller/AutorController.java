package br.ifms.edu.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import br.ifms.edu.demo.repository.AutorRepository;
import br.ifms.edu.demo.model.Autor;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AutorController {

    @Autowired
    private AutorRepository autorRepository;
// READ - Listar todos os autores
    @GetMapping("/autores")
    public String listarAutores(Model model) {
        model.addAttribute("listaAutores", autorRepository.findAll());
        return "lista-autores";
    }
// CREATE - Mostrar formulário para adicionar novo autor
    @GetMapping("/autores/novo")
    public String novoAutorForm(Model model) {
        model.addAttribute("autor", new Autor());
        return "form-autores";
    }
        // CREATE / UPDATE - Salvar autor
    @PostMapping("/autores/salvar")
    public String salvarAutor(@ModelAttribute("autor") Autor autor) {
        autorRepository.save(autor);
        return "redirect:/autores";
    }
    // UPDATE - Mostrar formulário para editar autor
    @GetMapping("/autores/editar/{id}")
    public String editarAutorForm(@PathVariable Long id, Model model) {
        Autor autor = autorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID de autor inválido:" + id));
        model.addAttribute("autor", autor);
        return "form-autores";  
    }
    // DELETE - Deletar autor
    @GetMapping("/autores/deletar/{id}")
    public String deletarAutor(@PathVariable Long id) {
        autorRepository.deleteById(id);
        return "redirect:/autores";
    } 
}