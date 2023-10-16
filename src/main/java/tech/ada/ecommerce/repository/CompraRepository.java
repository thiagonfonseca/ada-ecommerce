package tech.ada.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tech.ada.ecommerce.model.Compra;
import tech.ada.ecommerce.model.enums.StatusEnum;

@Repository
public interface CompraRepository extends JpaRepository<Compra, Long> {

    @Query("UPDATE Compra c SET c.status = :status")
    void atualizarStatus(@Param("status") StatusEnum status);

    @Query(value = "UPDATE compra SET status = :status", nativeQuery = true)
    void atualizarStatus(@Param("status") String status);


    @Query("SELECT c FROM Compra c INNER JOIN FETCH c.itens WHERE c.id = :id")
    Compra buscarCompraPorIdComProdutos(@Param("id") Long id);

}
