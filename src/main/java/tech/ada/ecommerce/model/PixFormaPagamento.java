package tech.ada.ecommerce.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import java.util.Objects;

@Entity
public class PixFormaPagamento extends FormaPagamento {

    @Column(unique = true, nullable = false)
    private String codigoPagamento;

    public PixFormaPagamento() {
        super();
    }

    public String getCodigoPagamento() {
        return codigoPagamento;
    }

    public void setCodigoPagamento(String codigoPagamento) {
        this.codigoPagamento = codigoPagamento;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        PixFormaPagamento that = (PixFormaPagamento) o;
        return Objects.equals(codigoPagamento, that.codigoPagamento);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), codigoPagamento);
    }
}
