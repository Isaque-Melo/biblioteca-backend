package br.ifms.edu.demo.service;

import java.time.LocalDate;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.ifms.edu.demo.repository.CartaoBibliotecaRepository;
import br.ifms.edu.demo.repository.LeitorRepository;
import jakarta.transaction.Transactional;
import br.ifms.edu.demo.dto.LeitorRegistroDTO;
import br.ifms.edu.demo.dto.LeitorResponseDTO;
import br.ifms.edu.demo.mapper.LeitorMapper;
import br.ifms.edu.demo.model.CartaoBiblioteca;
import br.ifms.edu.demo.model.Leitor;

@Service
public class LeitorService {

    @Autowired
    private LeitorRepository leitorRepository;
    @Autowired
    private LeitorMapper leitorMapper;
    @Autowired
    private CartaoBibliotecaRepository cartaoRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @Transactional
    public LeitorResponseDTO registrar(LeitorRegistroDTO dto){
        if (leitorRepository.existsByEmail(dto.email())) {
            throw new RuntimeException("Email já cadastrado");
        }
        CartaoBiblioteca cartao = new CartaoBiblioteca();
        Random random = new Random();
        int numeroCartao = random.nextInt(9000) + 1000;
        cartao.setNumero(String.valueOf(numeroCartao));
        cartao.setDataEmissao(LocalDate.now());
        cartaoRepository.save(cartao);

        Leitor leitor = leitorMapper.toEntity(dto);
        leitor.setPassword(passwordEncoder.encode(dto.senha()));
        leitor.setCartao(cartao);
        leitor.setRoles("Leitor");
        cartao.setLeitor(leitor); 
        cartaoRepository.save(cartao);
        return leitorMapper.toResponseDTO(leitor);
    }
    
}
