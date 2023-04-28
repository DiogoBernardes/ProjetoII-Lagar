package IPVC.DAL;

import jakarta.persistence.*;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;

@Entity
@Table(name="ProdutoEMB")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "produtoEMB.index", query = "SELECT produtoEMB FROM ProdutoEMB produtoEMB"),
        @NamedQuery(name = "produtoEMB.count", query = "SELECT count(produtoEMB) FROM ProdutoEMB produtoEMB"),
})
public class ProdutoEMB implements Serializable {

    @Id
    @ManyToOne
    @JoinColumn(name = "Id_Embalamento", referencedColumnName = "Id_Embalamento")
    private Embalamento embalamento;
    @Id
    @ManyToOne
    @JoinColumn(name = "Id_Produto", referencedColumnName = "Id_Produto")
    private Produto produto;
    @Basic
    @Column(name = "Quantidade", nullable = false)
    private int quantidade;

    public Embalamento getEmbalamento() {
        return embalamento;
    }
    public void setEmbalamento(Embalamento embalamento) {
        this.embalamento = embalamento;
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
}
