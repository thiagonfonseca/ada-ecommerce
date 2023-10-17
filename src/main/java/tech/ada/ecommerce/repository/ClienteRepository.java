package tech.ada.ecommerce.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tech.ada.ecommerce.model.Cliente;

import java.util.Date;
import java.util.List;

@Repository
@Transactional
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    List<Cliente> findByAtivo(boolean ativo);

    @Query("SELECT c FROM Cliente c WHERE c.nomeCompleto ILIKE concat('%', :nome, '%') ORDER BY c.nomeCompleto")
    List<Cliente> findByNomeCompletoCustom(@Param("nome") String nome);


    @Modifying
    @Query("UPDATE Cliente c SET c.ativo = :ativo WHERE c.id = :id")
    void ativarUsuario(@Param("ativo") boolean ativo, @Param("id") Long id);

//    List<Cliente> findByNomeCompleto(String nome);
//    List<Cliente> findByNomeCompletoLike(String nome);
//    Cliente findByDataNascimentoBetween(Date data1, Date data2);
//
//
//    @Query(value = "SELECT * FROM cliente ORDER BY NOME_COMPLETO", nativeQuery = true)
//    List<Cliente> findByCustom();
//
//    @Query(value = "SELECT * FROM cliente ORDER BY NOME_COMPLETO", nativeQuery = true)
//    Page<Cliente> findByCustom(Pageable pageable);
//
//
//
//    @Query(value = "UPDATE cliente SET ativo = :ativo WHERE id = :id", nativeQuery = true)
//    void ativarUsuario2(@Param("ativo") boolean ativo, @Param("id") Long id);
}