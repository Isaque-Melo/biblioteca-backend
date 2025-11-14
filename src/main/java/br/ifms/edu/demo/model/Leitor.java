package br.ifms.edu.demo.model;

import java.util.Collection;
import java.util.stream.Stream;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import jakarta.persistence.*;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Stream.of(this.role.split(","))
            .map(SimpleGrantedAuthority::new)
            .toList();
    }

    @Override
    public String getUsername() {
        return this.email;
    }
    @Override
    public String getPassword() {
        return this.password;
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    @Override
    public boolean isEnabled() {
        return true;
    }

    
}