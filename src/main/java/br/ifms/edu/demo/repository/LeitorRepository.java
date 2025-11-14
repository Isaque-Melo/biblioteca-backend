package br.ifms.edu.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import br.ifms.edu.demo.model.Leitor;

@Repository
public interface LeitorRepository extends JpaRepository<Leitor, Long> {

    Optional<Leitor> findByEmail(String email);
    Boolean existsByEmail(String email);
}
