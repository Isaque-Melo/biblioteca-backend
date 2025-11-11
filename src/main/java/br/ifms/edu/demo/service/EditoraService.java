package br.ifms.edu.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.ifms.edu.demo.dto.EditoraDTO;
import br.ifms.edu.demo.mapper.EditoraMapper;
import br.ifms.edu.demo.model.Editora;
import br.ifms.edu.demo.repository.EditoraRepository;
import br.ifms.edu.demo.repository.LivroRepository; 
// (Importe sua exceção personalizada quando a tiver)

@Service
public class EditoraService {

    private final EditoraRepository editoraRepository;
    private final LivroRepository livroRepository; // Precisamos para verificar a deleção
    private final EditoraMapper editoraMapper;

    @Autowired
    public EditoraService(
        EditoraRepository editoraRepository, 
        LivroRepository livroRepository, 
        EditoraMapper editoraMapper
    ) {
        this.editoraRepository = editoraRepository;
        this.livroRepository = livroRepository;
        this.editoraMapper = editoraMapper;
    }

    @Transactional(readOnly = true)
    public Page<EditoraDTO> listarEditoras(Pageable pageable) {

        Page<Editora> pagina = editoraRepository.findAll(pageable);
        return pagina.map(editoraMapper::toDTO);
        
    }

    @Transactional(readOnly = true)
    public EditoraDTO buscarPorId(Long id) {
        Editora editora = editoraRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Editora com ID " + id + " não encontrada."));
        return editoraMapper.toDTO(editora);
    }

    @Transactional
    public EditoraDTO criarEditora(EditoraDTO dto) {
        Editora editora = editoraMapper.toEntity(dto);
        Editora editoraSalva = editoraRepository.save(editora);
        return editoraMapper.toDTO(editoraSalva);
    }

    @Transactional
    public EditoraDTO atualizarEditora(Long id, EditoraDTO dto) {
        Editora editoraExistente = editoraRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Editora com ID " + id + " não encontrada para atualizar."));
        
        // Atualiza os campos
        editoraExistente.setNome(dto.nome());
        editoraExistente.setCidade(dto.cidade());
        
        Editora editoraAtualizada = editoraRepository.save(editoraExistente);
        return editoraMapper.toDTO(editoraAtualizada);
    }

    @Transactional
    public void deletarEditora(Long id) {
        if (!editoraRepository.existsById(id)) {
            throw new RuntimeException("Editora com ID " + id + " não encontrada para deletar.");
        }

        // --- LÓGICA DE NEGÓCIO ---
        // Não podemos deletar uma editora que tem livros publicados.
        // Precisamos criar um método no LivroRepository para fazer essa verificação.
        /* // Descomente isso quando adicionar o método no LivroRepository:
        if (livroRepository.countByEditoraId(id) > 0) {
            throw new RuntimeException("Não é possível deletar a editora pois ela possui livros associados.");
        }
        */
        
        // Se a validação passar, deleta.
        editoraRepository.deleteById(id);
    }
}