package tech.ada.ecommerce.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import java.util.Objects;

@Entity
public class BoletoFormaPagamento extends FormaPagamento {

    @Column(unique = true, nullable = false)
    private String codigoBoleto;

    public BoletoFormaPagamento() {
        super();
    }

    public String getCodigoBoleto() {
        return codigoBoleto;
    }

    public void setCodigoBoleto(String codigoBoleto) {
        this.codigoBoleto = codigoBoleto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        BoletoFormaPagamento that = (BoletoFormaPagamento) o;
        return Objects.equals(codigoBoleto, that.codigoBoleto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), codigoBoleto);
    }
}
