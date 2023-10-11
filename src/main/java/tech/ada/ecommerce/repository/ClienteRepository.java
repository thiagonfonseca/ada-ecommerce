package tech.ada.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tech.ada.ecommerce.model.Cliente;

import java.util.Date;
import java.util.List;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    List<Cliente> findByNomeCompleto(String nome);
    List<Cliente> findByNomeCompletoLike(String nome);
    Cliente findByDataNascimentoBetween(Date data1, Date data2);

    @Query("SELECT c FROM Cliente c WHERE c.nomeCompleto ILIKE concat('%', :nome, '%')")
    List<Cliente> findByNomeCompletoCustom(@Param("nome") String nome);

    @Query(value = "SELECT * FROM cliente", nativeQuery = true)
    List<Cliente> findByCustom();

    @Query("UPDATE Cliente c SET c.ativo = :ativo WHERE c.id = :id")
    void ativarUsuario(@Param("ativo") boolean ativo, @Param("id") Long id);


    @Query(value = "UPDATE cliente SET ativo = :ativo WHERE id = :id", nativeQuery = true)
    void ativarUsuario2(@Param("ativo") boolean ativo, @Param("id") Long id);
}