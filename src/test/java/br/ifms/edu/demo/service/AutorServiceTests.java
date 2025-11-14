package br.ifms.edu.demo.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import br.ifms.edu.demo.dto.AutorDTO;
import br.ifms.edu.demo.mapper.AutorMapper;
import br.ifms.edu.demo.model.Autor;
import br.ifms.edu.demo.model.Livro; // Precisamos para o teste de deleção
import br.ifms.edu.demo.repository.AutorRepository;

/**
 * Isto é um TESTE UNITÁRIO.
 * Testamos APENAS a classe AutorService.
 * Todas as suas dependências (Repository, Mapper) são "falsificadas" (mockadas).
 */
@ExtendWith(MockitoExtension.class) // Habilita o Mockito para criar os "dublês"
class AutorServiceTests {

    // Cria um "dublê" (Mock) do Repositório.
    // Ele NÃO vai usar o banco de dados.
    @Mock
    private AutorRepository autorRepository;

    // Cria um "dublê" (Mock) do Mapper.
    @Mock
    private AutorMapper autorMapper;

    // Injeta os "dublês" (@Mock) acima na nossa classe de serviço real.
    // É esta classe que estamos testando.
    @InjectMocks
    private AutorService autorService;


    @Test // Marcação do JUnit: isto é um método de teste.
    void testarListarAutores() {
        // --- 1. GIVEN (Dado um cenário) ---
        
        // Crie um objeto 'Pageable' de mentira
        Pageable pageable = PageRequest.of(0, 5); // Página 0, 5 elementos

        // Crie uma lista de Autores (Entidades) de mentira
        Autor autor1 = new Autor();
        autor1.setId(1L);
        autor1.setNome("Autor Teste 1");
        List<Autor> listaDeAutores = List.of(autor1);

        // Crie uma Página (Page) de mentira que o repositório deve retornar
        Page<Autor> paginaDeAutores = new PageImpl<>(listaDeAutores, pageable, 1);

        // Crie o DTO que o mapper deve retornar
        AutorDTO autorDTO = new AutorDTO(1L, "Autor Teste 1", "Nacionalidade Teste");

        // "Ensine" o dublê:
        // "QUANDO o autorRepository.findAll(qualquerPageable) for chamado,
        //  ENTÃO retorne a 'paginaDeAutores' que criamos."
        when(autorRepository.findAll(any(Pageable.class))).thenReturn(paginaDeAutores);
        
        // "QUANDO o autorMapper.toDTO(o 'autor1') for chamado,
        //  ENTÃO retorne o 'autorDTO' que criamos."
        when(autorMapper.toDTO(autor1)).thenReturn(autorDTO);

        // --- 2. WHEN (Quando eu executo a ação) ---
        // Chame o método real do serviço que queremos testar
        Page<AutorDTO> paginaResultado = autorService.listarAutores(pageable);

        // --- 3. THEN (Então eu verifico o resultado) ---
        assertNotNull(paginaResultado); // O resultado não pode ser nulo
        assertEquals(1, paginaResultado.getTotalElements()); // Deve ter 1 elemento
        assertEquals("Autor Teste 1", paginaResultado.getContent().get(0).nome());
        
        // Verifica se o repositório foi chamado exatamente 1 vez
        verify(autorRepository, times(1)).findAll(any(Pageable.class));
        verify(autorMapper, times(1)).toDTO(autor1);
    }

    @Test
    void testarBuscarPorId() {
        // --- 1. GIVEN (Cenário) ---
        Autor autorEntidade = new Autor();
        autorEntidade.setId(1L);
        autorEntidade.setNome("Autor Encontrado");
        
        AutorDTO autorDTO = new AutorDTO(1L, "Autor Encontrado", "Nac.");

        // "Ensine" o dublê:
        when(autorRepository.findById(1L)).thenReturn(Optional.of(autorEntidade));
        when(autorMapper.toDTO(autorEntidade)).thenReturn(autorDTO);

        // --- 2. WHEN (Ação) ---
        AutorDTO resultado = autorService.buscarPorId(1L);

        // --- 3. THEN (Verificação) ---
        assertNotNull(resultado);
        assertEquals(1L, resultado.id());
        assertEquals("Autor Encontrado", resultado.nome());
    }

    @Test
    void testarBuscarPorId_NaoEncontrado() {
        // --- 1. GIVEN (Cenário) ---
        // "Ensine" o dublê a NÃO encontrar nada
        when(autorRepository.findById(99L)).thenReturn(Optional.empty());

        // --- 2. WHEN (Ação) --- e --- 3. THEN (Verificação) ---
        
        // Verifique se a exceção correta é lançada
        // Ação: autorService.buscarPorId(99L)
        assertThrows(RuntimeException.class, () -> {
            autorService.buscarPorId(99L);
        });
    }

    @Test
    void testarCriarAutor() {
        // --- 1. GIVEN (Cenário) ---
        // Este é o DTO que vem do Controller
        AutorDTO dtoEntrada = new AutorDTO(null, "Novo Autor", "Nac. Nova");

        // Esta é a entidade que o mapper deve "criar"
        Autor autorSemId = new Autor();
        autorSemId.setNome("Novo Autor");
        
        // Esta é a entidade que o repositório "salva" (agora com ID)
        Autor autorComId = new Autor();
        autorComId.setId(1L);
        autorComId.setNome("Novo Autor");

        // Este é o DTO que o mapper deve retornar no final
        AutorDTO dtoSaida = new AutorDTO(1L, "Novo Autor", "Nac. Nova");

        // "Ensine" os dublês:
        when(autorMapper.toEntity(dtoEntrada)).thenReturn(autorSemId);
        when(autorRepository.save(autorSemId)).thenReturn(autorComId);
        when(autorMapper.toDTO(autorComId)).thenReturn(dtoSaida);
        
        // --- 2. WHEN (Ação) ---
        AutorDTO resultado = autorService.criarAutor(dtoEntrada);

        // --- 3. THEN (Verificação) ---
        assertNotNull(resultado);
        assertEquals(1L, resultado.id());
        assertEquals("Novo Autor", resultado.nome());

        // Verifica a ordem das chamadas
        verify(autorMapper).toEntity(dtoEntrada);
        verify(autorRepository).save(autorSemId);
        verify(autorMapper).toDTO(autorComId);
    }
    
    @Test
    void testarDeletarAutor() {
        // --- 1. GIVEN (Cenário) ---
        // Este é o teste mais importante da sua lógica de negócio!
        Autor autor = new Autor();
        autor.setId(1L);

        // Crie um livro de mentira e adicione-o ao autor
        Livro livro1 = new Livro();
        livro1.setId(10L);
        livro1.setAutores(new ArrayList<>(List.of(autor))); // Lista mutável
        
        // Adicione o livro à lista de livros do autor
        autor.setLivros(List.of(livro1));
        
        // "Ensine" o dublê:
        when(autorRepository.findById(1L)).thenReturn(Optional.of(autor));
        // Para métodos void (sem retorno), use doNothing()
        doNothing().when(autorRepository).delete(autor);

        // --- 2. WHEN (Ação) ---
        autorService.deletarAutor(1L);

        // --- 3. THEN (Verificação) ---
        
        // Verifique se os métodos corretos foram chamados
        verify(autorRepository, times(1)).findById(1L);
        verify(autorRepository, times(1)).delete(autor);
        
        // **A VERIFICAÇÃO MAIS IMPORTANTE**
        // Verifique se a lógica de negócio (desassociar o livro) foi executada
        assertTrue(livro1.getAutores().isEmpty(), "O autor não foi removido da lista de autores do livro!");
    }
}