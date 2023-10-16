package tech.ada.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.ada.ecommerce.model.ItemProduto;

@Repository
public interface ItemProdutoRepository extends JpaRepository<ItemProduto, Long> {
}
