package IPVC.DAL;

import jakarta.persistence.*;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;

@Entity
@Table(name="ProdutoMP")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "produtoMP.index", query = "SELECT produtoMP FROM ProdutoMP produtoMP"),
        @NamedQuery(name = "produtoMP.count", query = "SELECT count(produtoMP) FROM ProdutoMP produtoMP"),
})
public class ProdutoMP implements Serializable {
    @Id
    @ManyToOne
    @JoinColumn(name = "Id_Producao", referencedColumnName = "Id_Producao")
    private Producao producao;
    @Id
    @ManyToOne
    @JoinColumn(name = "Id_Produto", referencedColumnName = "Id_Produto")
    private Produto produto;
    @Basic
    @Column(name = "Quantidade", nullable = false)
    private int quantidade;

    public Producao getProducao() {
        return producao;
    }

    public void setProducao(Producao producao) {
        this.producao = producao;
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
