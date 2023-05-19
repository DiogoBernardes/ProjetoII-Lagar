package IPVC.DAL;

import jakarta.persistence.*;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="Fatura")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "fatura.index", query = "SELECT fatura FROM Fatura fatura WHERE fatura.deleted_on = null"),
        @NamedQuery(name = "fatura.count", query = "SELECT count(fatura) FROM Fatura fatura WHERE fatura.deleted_on = null"),
})
public class Fatura {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_Fatura")
    private int Id_Fatura;

    @Basic
    @Column(name = "Data_Emissao", nullable = false)
    private Date Data;
    @Basic
    @Column(name = "Valor", nullable = false)
    private double Valor;

    @Basic
    @Column(name = "Valor_Total", nullable = false)
    private double Valor_Total;
    @Basic
    @Column(name = "IVA", nullable = false)
    private double iva;

    @Basic
    @Column(name = "deleted_on")
    private Timestamp deleted_on;
    @ManyToOne
    @JoinColumn(name = "Id_TipoPagamento",referencedColumnName = "Id_TipoPagamento")
    private TipoPagamento tipoPagamento;
    @ManyToOne
    @JoinColumn(name = "Id_Utilizador",referencedColumnName = "Id_Utilizador")
    private Utilizador utilizador;
    @ManyToOne
    @JoinColumn(name = "Id_Entidade",referencedColumnName = "Id_Entidade")
    private Entidade entidade;
    @OneToMany(mappedBy = "fatura")
    private List<LinhaFatura> linhaFaturas;

    public int getId_Fatura() {
        return Id_Fatura;
    }
    public void setId_Fatura(int id_Fatura) {
        Id_Fatura = id_Fatura;
    }
    public Date getData() {
        return Data;
    }
    public void setData(Date data) {
        Data = data;
    }
    public double getValor() {
        return Valor;
    }
    public void setValor(double valor) {
        Valor = valor;
    }
    public double getValor_Total() {
        return Valor_Total;
    }
    public void setValor_Total(double Valor_Total) {
        this.Valor_Total = Valor_Total;
    }
    public double getIva() {
        return iva;
    }
    public void setIva(double iva) {
        this.iva = iva;
    }
    public TipoPagamento getTipoPagamento() {
        return tipoPagamento;
    }
    public void setTipoPagamento(TipoPagamento tipoPagamento) {
        this.tipoPagamento = tipoPagamento;
    }
    public Utilizador getUtilizador() {
        return utilizador;
    }
    public void setUtilizador(Utilizador utilizador) {
        this.utilizador = utilizador;
    }
    public Entidade getEntidade() {
        return entidade;
    }
    public void setEntidade(Entidade entidade) {
        this.entidade = entidade;
    }

    public Timestamp getDeleted_on() {
        return deleted_on;
    }

    public void setDeleted_on(Timestamp deleted_on) {
        this.deleted_on = deleted_on;
    }
}
