package br.ifms.edu.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.ifms.edu.demo.dto.AutorDTO;
import br.ifms.edu.demo.mapper.AutorMapper;
import br.ifms.edu.demo.model.Autor;
import br.ifms.edu.demo.model.Livro;
import br.ifms.edu.demo.repository.AutorRepository;


@Service 
public class AutorService {

    private final AutorRepository autorRepository;
    private final AutorMapper autorMapper;

    @Autowired
    public AutorService(AutorRepository autorRepository, AutorMapper autorMapper) {
        this.autorRepository = autorRepository;
        this.autorMapper = autorMapper;
    }

    /**
     * Busca todos os autores de forma paginada.
     * @param pageable Objeto de paginação (ex: ?page=0&size=10&sort=nome,asc)
     * @return Uma Página (Page) de AutorDTO.
     */
    @Transactional(readOnly = true)
    public Page<AutorDTO> listarAutores(Pageable pageable) {
        Page<Autor> paginaDeAutores = autorRepository.findAll(pageable);
        
        return paginaDeAutores.map(autorMapper::toDTO);
    }

    @Transactional(readOnly = true)
    public AutorDTO buscarPorId(Long id) {
        Autor autor = autorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Autor não encontrado"));
        return autorMapper.toDTO(autor);
    }

    @Transactional
    public AutorDTO criarAutor(AutorDTO autorDTO) {
        Autor autor = autorMapper.toEntity(autorDTO);
        Autor autorSalvo = autorRepository.save(autor);
        return autorMapper.toDTO(autorSalvo);
    }

    @Transactional
    public AutorDTO atualizarAutor(Long id, AutorDTO autorDTO) {
        Autor autorParaAtualizar = autorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Autor não encontrado para atualizar"));
        
        // Mapeia os dados do DTO para a entidade existente
        autorParaAtualizar.setNome(autorDTO.nome());
        autorParaAtualizar.setNacionalidade(autorDTO.nacionalidade());
        
        Autor autorAtualizado = autorRepository.save(autorParaAtualizar);
        return autorMapper.toDTO(autorAtualizado);
    }

    @Transactional
    public void deletarAutor(Long id) {
        Autor autor = autorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Autor não encontrado"));

        // Dissociar o autor dos livros
        for (Livro livro : autor.getLivros()) {
            livro.getAutores().remove(autor);
        }
        
        autorRepository.delete(autor);
    }
}