package br.ifms.edu.demo.Controller;

import br.ifms.edu.demo.model.Editora;
import br.ifms.edu.demo.repository.EditoraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class EditoraController {

    @Autowired
    private EditoraRepository editoraRepository;

    // READ - Listar todas as editoras
    @GetMapping("/editoras")
    public String listarEditoras(Model model) {
        model.addAttribute("listaEditoras", editoraRepository.findAll());
        return "lista-editoras";
    }

    // CREATE - Mostrar formulário para adicionar nova editora
    @GetMapping("/editoras/novo")
    public String mostrarFormularioDeNovaEditora(Model model) {
        model.addAttribute("editora", new Editora());
        return "form-editora";
    }

    // CREATE / UPDATE - Salvar editora
    @PostMapping("/editoras/salvar")
    public String salvarEditora(@ModelAttribute("editora") Editora editora) {
        editoraRepository.save(editora);
        return "redirect:/editoras";
    }

    // UPDATE - Mostrar formulário para editar editora
    @GetMapping("/editoras/editar/{id}")
    public String mostrarFormularioDeEditarEditora(@PathVariable Long id, Model model) {
        Editora editora = editoraRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID de editora inválido:" + id));
        model.addAttribute("editora", editora);
        return "form-editora";
    }

    // DELETE - Deletar editora
    @GetMapping("/editoras/deletar/{id}")
    public String deletarEditora(@PathVariable Long id) {
        editoraRepository.deleteById(id);
        return "redirect:/editoras";
    }
}
