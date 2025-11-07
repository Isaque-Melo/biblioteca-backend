package br.ifms.edu.demo.model;

import jakarta.persistence.*;

@Entity
public class Leitor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String email;

    // Lado dono do relacionamento
    @OneToOne(cascade = CascadeType.ALL) // cascade: se eu salvar um leitor, salva o cartão junto.
    @JoinColumn(name = "cartao_id", referencedColumnName = "id") // Define a chave estrangeira na tabela Leitor
    private CartaoBiblioteca cartao;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public CartaoBiblioteca getCartao() {
        return cartao;
    }

    public void setCartao(CartaoBiblioteca cartao) {
        this.cartao = cartao;
    }
}