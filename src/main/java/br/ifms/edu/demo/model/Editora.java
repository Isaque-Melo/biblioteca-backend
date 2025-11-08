package br.ifms.edu.demo.model;

import jakarta.persistence.*;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class Editora {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String cidade;

    @JsonManagedReference
    @OneToMany(mappedBy = "editora", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Livro> livros;

    // Getters e Setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getCidade() {
        return cidade;
    }
    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

     @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Editora editora = (Editora) o;
        return Objects.equals(id, editora.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
