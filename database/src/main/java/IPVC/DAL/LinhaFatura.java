package IPVC.DAL;

import jakarta.persistence.*;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name="Linha_Fatura")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "linhaFatura.index", query = "SELECT linhaFatura FROM LinhaFatura linhaFatura WHERE linhaFatura.deleted_on = null"),
        @NamedQuery(name = "linhaFatura.count", query = "SELECT count(linhaFatura) FROM LinhaFatura linhaFatura WHERE linhaFatura.deleted_on = null"),
        @NamedQuery(name = "linhaFatura.findByFatura", query = "SELECT linhaFatura FROM LinhaFatura linhaFatura WHERE linhaFatura.fatura.Id_Fatura = :idFatura and linhaFatura.deleted_on = null"),
})
public class LinhaFatura implements Serializable {

    @Id
    @ManyToOne
    @JoinColumn(name = "Id_Produto",referencedColumnName = "id_Produto")
    private Produto produto;

    @Id
    @ManyToOne
    @JoinColumn(name = "Id_Fatura",referencedColumnName = "Id_Fatura")
    private Fatura fatura;

    @Basic
    @Column(name = "Quantidade", nullable = false)
    private int quantidade;
    @Basic
    @Column(name = "Valor", nullable = false)
    private double valor;

    @Basic
    @Column(name = "deleted_on")
    private Timestamp deleted_on;

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

    public Timestamp getDeleted_on() {
        return deleted_on;
    }

    public void setDeleted_on(Timestamp deleted_on) {
        this.deleted_on = deleted_on;
    }
}
