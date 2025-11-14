package br.ifms.edu.demo.model;

import org.springframework.security.core.userdetails.UserDetails;
import lombok.Getter;
import lombok.Setter;


import jakarta.persistence.*;

@Entity
@Setter
@Getter
public class Leitor implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    @Column(unique = true)
    private String email;
    private String password;

    private String role = "Leitor";

    // Lado dono do relacionamento
    @OneToOne(cascade = CascadeType.ALL) // cascade: se eu salvar um leitor, salva o cartão junto.
    @JoinColumn(name = "cartao_id", referencedColumnName = "id") // Define a chave estrangeira na tabela Leitor
    private CartaoBiblioteca cartao;

    
}