package br.ifms.edu.demo.config;

import java.time.LocalDate;
import java.util.Random;
import java.util.UUID;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import br.ifms.edu.demo.model.CartaoBiblioteca;
import br.ifms.edu.demo.model.Leitor;
import br.ifms.edu.demo.repository.CartaoBibliotecaRepository;
import br.ifms.edu.demo.repository.LeitorRepository;

@Configuration
public class AdminUserConfig implements CommandLineRunner {

    private final LeitorRepository leitorRepository;
    private final CartaoBibliotecaRepository cartaoRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminUserConfig(LeitorRepository leitorRepository, 
                           CartaoBibliotecaRepository cartaoRepository, 
                           PasswordEncoder passwordEncoder) {
        this.leitorRepository = leitorRepository;
        this.cartaoRepository = cartaoRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        // Verifica se já existe um admin para não criar duplicado
        var adminEmail = "admin@biblioteca.com";
        
        if (leitorRepository.findByEmail(adminEmail).isPresent()) {
            System.out.println("O usuário Admin já existe.");
            return;
        }

        System.out.println("Criando usuário Admin...");

        // Cria o cartão para o admin (regra do sistema)
        CartaoBiblioteca cartao = new CartaoBiblioteca();
        Random random = new Random();
        int numeroCartao = random.nextInt(9000) + 1000;
        cartao.setNumero(String.valueOf(numeroCartao));
        cartao.setDataEmissao(LocalDate.now());
        cartaoRepository.save(cartao);

        Leitor admin = new Leitor();
        admin.setNome("Administrador");
        admin.setEmail(adminEmail);
        admin.setPassword(passwordEncoder.encode("admin123"));
        admin.setRoles("ROLE_ADMIN"); 
        admin.setCartao(cartao);

        leitorRepository.save(admin);
        
        cartao.setLeitor(admin);
        cartaoRepository.save(cartao);

        System.out.println("Usuário Admin criado com sucesso: " + adminEmail);
    }
}