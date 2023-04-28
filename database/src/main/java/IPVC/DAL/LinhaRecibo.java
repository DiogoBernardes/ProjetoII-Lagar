package IPVC.DAL;
import jakarta.persistence.*;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;

@Entity
@Table(name="Linha_Recibo")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "linhaRecibo.index", query = "SELECT linhaRecibo FROM LinhaRecibo linhaRecibo"),
        @NamedQuery(name = "linhaRecibo.count", query = "SELECT count(linhaRecibo) FROM LinhaRecibo linhaRecibo"),
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
}
