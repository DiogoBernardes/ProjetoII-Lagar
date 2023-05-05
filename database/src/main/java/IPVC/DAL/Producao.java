package IPVC.DAL;

import jakarta.persistence.*;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name="Producao")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "producao.index", query = "SELECT producao FROM Producao producao WHERE producao.deleted_on = null"),
        @NamedQuery(name = "producao.count", query = "SELECT count(producao) FROM Producao producao WHERE producao.deleted_on = null"),
})
public class Producao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_Producao")
    private int id_Producao;

    @Basic
    @Column(name = "Data", nullable = false)
    private Date data;
    @Basic
    @Column(name = "Qtd_Produzida", nullable = false)
    private double qtd_Produzida;
    @Basic
    @Column(name = "Acidez", nullable = false)
    private double acidez;
    @Basic
    @Column(name = "deleted_on")
    private Timestamp deleted_on;
    @ManyToOne
    @JoinColumn(name = "Id_Produto",referencedColumnName = "Id_Produto")
    private Produto produto;

    public int getId_Producao() {
        return id_Producao;
    }

    public void setId_Producao(int id_Producao) {
        this.id_Producao = id_Producao;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public double getQtd_Produzida() {
        return qtd_Produzida;
    }

    public void setQtd_Produzida(double qtd_Produzida) {
        this.qtd_Produzida = qtd_Produzida;
    }

    public double getAcidez() {
        return acidez;
    }

    public void setAcidez(double acidez) {
        this.acidez = acidez;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Timestamp getDeleted_on() {
        return deleted_on;
    }

    public void setDeleted_on(Timestamp deleted_on) {
        this.deleted_on = deleted_on;
    }
}
