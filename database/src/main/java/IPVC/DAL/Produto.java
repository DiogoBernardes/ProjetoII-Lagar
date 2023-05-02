package IPVC.DAL;

import jakarta.persistence.*;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.List;

@Entity
@Table(name="Produto")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "produto.index", query = "SELECT produto FROM Produto produto"),
        @NamedQuery(name = "produto.count", query = "SELECT count(produto) FROM Produto produto"),
        @NamedQuery(name = "produto.getName", query = "SELECT produto FROM Produto produto WHERE produto.Nome = :Nome"),
        @NamedQuery(name = "produto.getTypeProduct", query = "SELECT produto FROM Produto produto WHERE produto.tipoProduto.id_TipoProduto = :idTipoProduto"),

})
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_Produto")
    private int Id_Produto;

    @Basic
    @Column(name = "Nome", nullable = false)
    private String Nome;
    @Basic
    @Column(name = "Valor_Unitario", nullable = false)
    private double Valor_Unitario;

    @Basic
    @Column(name = "Quantidade", nullable = false)
    private int Quantidade;

    @ManyToOne
    @JoinColumn(name = "Id_TipoProduto",referencedColumnName = "Id_TipoProduto")
    private TipoProduto tipoProduto;

    @Basic
    @Column(name = "Unidade", nullable = false)
    private String Unidade;

    @OneToMany(mappedBy = "produto")
    private List<LinhaRecibo> linhaRecibos;
    @OneToMany(mappedBy = "produto")
    private List<LinhaFatura> linhaFaturas;
    @OneToMany(mappedBy = "produto")
    private List<Embalamento> embalamentos;
    @OneToMany(mappedBy = "produto")
    private List<ProdutoEMB> produtoEMBS;
    @OneToMany(mappedBy = "produto")
    private List<Producao> producoes;
    @OneToMany(mappedBy = "produto")
    private List<ProdutoMP> produtoMPS;

    public int getId_Produto() {
        return Id_Produto;
    }

    public void setId_Produto(int id_Produto) {
        Id_Produto = id_Produto;
    }

    public String getNome() {
        return Nome;
    }

    public void setNome(String nome) {
        Nome = nome;
    }

    public double getValor_Unitario() {
        return Valor_Unitario;
    }

    public void setValor_Unitario(double valor_Unitario) {
        Valor_Unitario = valor_Unitario;
    }

    public int getQuantidade() {
        return Quantidade;
    }

    public void setQuantidade(int quantidade) {
        Quantidade = quantidade;
    }

    public String getUnidade() {
        return Unidade;
    }

    public void setUnidade(String unidade) {
        Unidade = unidade;
    }

    public TipoProduto getTipoProduto() {
        return tipoProduto;
    }

    public void setTipoProduto(TipoProduto tipoProduto) {

        this.tipoProduto = tipoProduto;
    }
}
