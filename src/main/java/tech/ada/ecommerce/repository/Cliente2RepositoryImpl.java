package tech.ada.ecommerce.repository;

import com.querydsl.jpa.JPQLTemplates;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import tech.ada.ecommerce.model.Cliente;
import tech.ada.ecommerce.model.QCliente;

import java.util.List;

@Repository
public class Cliente2RepositoryImpl implements Cliente2Repository {

    private final EntityManager entityManager;
    private final JPAQueryFactory queryFactory;
    private final QCliente cliente;

    public Cliente2RepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.queryFactory = new JPAQueryFactory(JPQLTemplates.DEFAULT, entityManager);
        this.cliente = QCliente.cliente;
    }

    @Override
    public List<Cliente> findAll() {
        List<Cliente> clientes = queryFactory.selectFrom(this.cliente).fetch();
        return clientes;
    }

    @Override
    public Cliente findById(Long id) {
        Cliente cliente = queryFactory.selectFrom(this.cliente)
                .where(this.cliente.id.eq(id))
                .fetchFirst();
        return cliente;
    }

    @Override
    public List<Cliente> findByNome(String nome) {
        List<Cliente> clientes = queryFactory.selectFrom(this.cliente)
                .where(this.cliente.nomeCompleto.like("%"+nome+"%"))
                .orderBy(this.cliente.nomeCompleto.asc()).fetch();
        return clientes;
    }
}
