package br.ifms.edu.demo.dto;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class LivroFormDTO {
    @Schema(description = "Título do livro", example = "Dom Casmurro")
    @NotBlank(message = "O título do livro não pode ser vazio")
    @Size(min = 2, message = "O título do livro deve ter no mínimo 2 caracteres")
    private String titulo;


    @Schema(description = "Ano de publicação do livro", example = "1899")
    @NotBlank(message = "O ano de publicação do livro não pode ser vazio")
    @Min(value = 1, message = "O ano de publicação deve ser um número positivo")
    private int anoPublicacao;


    @Schema(description = "ID da editora do livro", example = "1")
    @NotNull(message = "O ID da editora não pode ser nulo")
    private Long editoraId;


    @Schema(description = "Lista de IDs dos autores do livro", example = "[1, 2]")
    @NotEmpty(message = "A lista de IDs dos autores não pode ser vazia")
    private List<Long> autoresIds;
    
    // Getters e Setters
    public String getTitulo() {
        return titulo;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    public int getAnoPublicacao() {
        return anoPublicacao;
    }
    public void setAnoPublicacao(int anoPublicacao) {
        this.anoPublicacao = anoPublicacao;
    }
    public Long getEditoraId() {
        return editoraId;
    }
    public void setEditoraId(Long editoraId) {
        this.editoraId = editoraId;
    }
    public List<Long> getAutoresIds() {
        return autoresIds;
    }
    public void setAutoresIds(List<Long> autoresIds) {
        this.autoresIds = autoresIds;
    }
}
