package IPVC.DAL;

import jakarta.persistence.*;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name="Embalamento")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "embalamento.index", query = "SELECT embalamento FROM Embalamento embalamento WHERE embalamento.deleted_on = null"),
        @NamedQuery(name = "embalamento.count", query = "SELECT count(embalamento) FROM Embalamento embalamento WHERE embalamento.deleted_on = null"),
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

    @Basic
    @Column(name = "deleted_on")
    private Timestamp deleted_on;
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

    public Timestamp getDeleted_on() {
        return deleted_on;
    }

    public void setDeleted_on(Timestamp deleted_on) {
        this.deleted_on = deleted_on;
    }

    @Override
    public String toString() {
        return String.valueOf(id_Embalamento);
    }
}
