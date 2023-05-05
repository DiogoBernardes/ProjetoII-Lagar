package IPVC.DAL;
import jakarta.persistence.*;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name="Linha_Recibo")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "linhaRecibo.index", query = "SELECT linhaRecibo FROM LinhaRecibo linhaRecibo WHERE linhaRecibo.deleted_on = null"),
        @NamedQuery(name = "linhaRecibo.count", query = "SELECT count(linhaRecibo) FROM LinhaRecibo linhaRecibo WHERE linhaRecibo.deleted_on = null"),
})
public class LinhaRecibo implements Serializable {

    @Id
    @ManyToOne
    @JoinColumn(name = "Id_Recibo",referencedColumnName = "Id_Recibo")
    private Recibo recibo;
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

    @Basic
    @Column(name = "deleted_on")
    private Timestamp deleted_on;

    public Recibo getRecibo() {
        return recibo;
    }
    public void setRecibo(Recibo recibo) {
        this.recibo = recibo;
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
