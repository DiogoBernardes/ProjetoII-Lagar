package IPVC.DAL;

import jakarta.persistence.*;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name="ProdutoEMB")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "produtoEMB.index", query = "SELECT produtoEMB FROM ProdutoEMB produtoEMB WHERE produtoEMB.deleted_on = null"),
        @NamedQuery(name = "produtoEMB.count", query = "SELECT count(produtoEMB) FROM ProdutoEMB produtoEMB WHERE produtoEMB.deleted_on = null"),
        @NamedQuery(name = "produtoEMB.findByEmbalamento", query = "SELECT produtoEMB FROM ProdutoEMB produtoEMB WHERE produtoEMB.embalamento.id_Embalamento = :idEmbalamento and produtoEMB.deleted_on = null"),
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

    @Basic
    @Column(name = "deleted_on")
    private Timestamp deleted_on;

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

    public Timestamp getDeleted_on() {
        return deleted_on;
    }
    public void setDeleted_on(Timestamp deleted_on) {
        this.deleted_on = deleted_on;
    }
}
