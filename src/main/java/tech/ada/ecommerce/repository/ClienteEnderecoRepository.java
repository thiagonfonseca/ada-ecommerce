package tech.ada.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.ada.ecommerce.model.ClienteEndereco;

public interface ClienteEnderecoRepository extends JpaRepository<ClienteEndereco, Long> {
}
