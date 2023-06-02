package IPVC.DAL;

import jakarta.persistence.*;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name="Produto")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "produto.index", query = "SELECT produto FROM Produto produto WHERE produto.deleted_on = null"),
        @NamedQuery(name = "produto.count", query = "SELECT count(produto) FROM Produto produto WHERE produto.deleted_on = null"),
        @NamedQuery(name = "produto.getName", query = "SELECT produto FROM Produto produto WHERE produto.Nome = :Nome and produto.deleted_on = null"),
        @NamedQuery(name = "produto.getTypeProduct", query = "SELECT produto FROM Produto produto WHERE produto.tipoProduto.id_TipoProduto = :idTipoProduto and produto.deleted_on = null"),

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

    @Basic
    @Column(name = "imagem")
    private String Imagem;

    @Basic
    @Column(name = "deleted_on")
    private Timestamp deleted_on;
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

    public Timestamp getDeleted_on() {
        return deleted_on;
    }

    public void setDeleted_on(Timestamp deleted_on) {
        this.deleted_on = deleted_on;
    }

    public String getImagem() {
        return Imagem;
    }

    public void setImagem(String imagem) {
        Imagem = imagem;
    }
}
