package tech.ada.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.ada.ecommerce.model.Endereco;

public interface EnderecoRepository extends JpaRepository<Endereco, Long> {
}
