package br.ifms.edu.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.ifms.edu.demo.dto.LivroFormDTO;
import br.ifms.edu.demo.dto.LivroResponseDTO;
import br.ifms.edu.demo.mapper.LivroMapper;
import br.ifms.edu.demo.model.Autor;
import br.ifms.edu.demo.model.Editora;
import br.ifms.edu.demo.model.Livro;
import br.ifms.edu.demo.repository.AutorRepository;
import br.ifms.edu.demo.repository.EditoraRepository;
import br.ifms.edu.demo.repository.LivroRepository;

import java.util.List;

@Service
public class LivroService {

    // Injeção de todas as dependências necessárias
    private final LivroRepository livroRepository;
    private final EditoraRepository editoraRepository;
    private final AutorRepository autorRepository;
    private final LivroMapper livroMapper;

    @Autowired
    public LivroService(
        LivroRepository livroRepository, 
        EditoraRepository editoraRepository, 
        AutorRepository autorRepository, 
        LivroMapper livroMapper
    ) {
        this.livroRepository = livroRepository;
        this.editoraRepository = editoraRepository;
        this.autorRepository = autorRepository;
        this.livroMapper = livroMapper;
    }

    /**
     * Lista todos os livros de forma paginada.
     * Usa a query customizada 'findAllComDetalhes' para evitar o N+1.
     */
    @Transactional(readOnly = true)
    public Page<LivroResponseDTO> listarLivros(Pageable pageable) {
        Page<Livro> paginaLivros = livroRepository.findAllDetalhes(pageable);
        
        return paginaLivros.map(livroMapper::toResponseDTO);
    }

    /**
     * Busca um único livro pelo seu ID.
     */
    @Transactional(readOnly = true)
    public LivroResponseDTO buscarPorId(Long id) {
        // (Quando criar sua exceção, troque RuntimeException por ela)
        Livro livro = livroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Livro com ID " + id + " não encontrado."));
        
        return livroMapper.toResponseDTO(livro);
    }

    /**
     * Cria um novo livro no banco de dados.
     * A lógica de busca por Editora/Autores é feita pelo LivroMapper.
     */
    @Transactional
    public LivroResponseDTO criarLivro(LivroFormDTO dto) {
        Livro livro = livroMapper.toEntity(dto);
        
        Livro livroSalvo = livroRepository.save(livro);
        
        return livroMapper.toResponseDTO(livroSalvo);
    }
    @Transactional
    public LivroResponseDTO atualizarLivro(Long id, LivroFormDTO dto) {
        Livro livroExistente = livroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Livro com ID " + id + " não encontrado para atualizar."));

        Editora editora = editoraRepository.findById(dto.getEditoraId())
                .orElseThrow(() -> new RuntimeException("Editora com ID " + dto.getEditoraId() + " não encontrada."));
        
        List<Autor> autores = autorRepository.findAllById(dto.getAutoresIds());
        
        livroExistente.setTitulo(dto.getTitulo());
        livroExistente.setAnoPublicacao(dto.getAnoPublicacao());
        livroExistente.setEditora(editora);
        livroExistente.setAutores(autores);

        Livro livroAtualizado = livroRepository.save(livroExistente);

        return livroMapper.toResponseDTO(livroAtualizado);
    }

    /**
     * Deleta um livro pelo seu ID.
     */
    @Transactional
    public void deletarLivro(Long id) {
        if (!livroRepository.existsById(id)) {
            throw new RuntimeException("Livro com ID " + id + " não encontrado para deletar.");
        }
        
        livroRepository.deleteById(id);
    }
}