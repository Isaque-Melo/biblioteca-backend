package br.ifms.edu.demo.dto;

import java.util.List;

public class LivroFormDTO {
    private String titulo;
    private int anoPublicacao;
    private Long editoraId;
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
