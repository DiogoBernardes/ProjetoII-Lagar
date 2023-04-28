package IPVC.DAL;

import jakarta.persistence.*;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;

@Entity
@Table(name="Linha_Fatura")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "linhaFatura.index", query = "SELECT linhaFatura FROM LinhaFatura linhaFatura"),
        @NamedQuery(name = "linhaFatura.count", query = "SELECT count(linhaFatura) FROM LinhaFatura linhaFatura"),
})
public class LinhaFatura implements Serializable {
    @Id
    @ManyToOne
    @JoinColumn(name = "Id_Fatura",referencedColumnName = "Id_Fatura")
    private Fatura fatura;
    @Id
    @ManyToOne
    @JoinColumn(name = "Id_Produto",referencedColumnName = "Id_Produto")
    private Produto produto;
    @Basic
    @Column(name = "Quantidade", nullable = false)
    private int quantidade;
    @Basic
    @Column(name = "Valor", nullable = false)
    private double valor;

    public Fatura getFatura() {
        return fatura;
    }
    public void setFatura(Fatura fatura) {
        this.fatura = fatura;
    }
    public Produto getProduto() {
        return produto;
    }
    public void setProduto(Produto produto) {
        this.produto = produto;
    }
    public int getQuantidade() {
        return quantidade;
    }
    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
    public double getValor() {
        return valor;
    }
    public void setValor(double valor) {
        this.valor = valor;
    }
}
