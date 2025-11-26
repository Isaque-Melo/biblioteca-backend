package br.ifms.edu.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.ifms.edu.demo.repository.LeitorRepository;

@Service
public class DetalheUsuarioServiceImp implements UserDetailsService{

    @Autowired
    private LeitorRepository leitorRepository;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return leitorRepository.findByEmail(username)
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }
    
}
    