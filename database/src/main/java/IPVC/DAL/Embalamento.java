package IPVC.DAL;

import jakarta.persistence.*;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.Date;

@Entity
@Table(name="Embalamento")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "embalamento.index", query = "SELECT embalamento FROM Embalamento embalamento"),
        @NamedQuery(name = "embalamento.count", query = "SELECT count(embalamento) FROM Embalamento embalamento"),
})
public class Embalamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_Embalamento")
    private int id_Embalamento;

    @Basic
    @Column(name = "Data", nullable = false)
    private Date data;
    @Basic
    @Column(name = "Qtd_Embalada", nullable = false)
    private double qtd_Embalada;
    @ManyToOne
    @JoinColumn(name = "Id_Produto",referencedColumnName = "Id_Produto")
    private Produto produto;

    public int getId_Embalamento() {
        return id_Embalamento;
    }
    public void setId_Embalamento(int id_Embalamento) {
        this.id_Embalamento = id_Embalamento;
    }
    public Date getData() {
        return data;
    }
    public void setData(Date data) {
        this.data = data;
    }
    public double getQtd_Embalada() {
        return qtd_Embalada;
    }
    public void setQtd_Embalada(double qtd_Embalada) {
        this.qtd_Embalada = qtd_Embalada;
    }
    public Produto getProduto() {
        return produto;
    }
    public void setProduto(Produto produto) {
        this.produto = produto;
    }
}
