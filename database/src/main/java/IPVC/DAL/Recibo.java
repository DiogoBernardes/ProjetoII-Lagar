package IPVC.DAL;

import jakarta.persistence.*;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.Date;
import java.util.List;

@Entity
@Table(name="Recibo")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "recibo.index", query = "SELECT recibo FROM Recibo recibo"),
        @NamedQuery(name = "recibo.count", query = "SELECT count(recibo) FROM Recibo recibo"),
})
public class Recibo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_Recibo")
    private int id_Recibo;

    @Basic
    @Column(name = "Data_Emissao", nullable = false)
    private Date data;
    @Basic
    @Column(name = "Valor", nullable = false)
    private double valor;

    @Basic
    @Column(name = "Valor_Final", nullable = false)
    private double valor_Final;
    @Basic
    @Column(name = "IVA", nullable = false)
    private double iva;

    @ManyToOne
    @JoinColumn(name = "Id_TipoPagamento",referencedColumnName = "Id_TipoPagamento")
    private TipoPagamento tipoPagamento;
    @ManyToOne
    @JoinColumn(name = "Id_Entidade",referencedColumnName = "Id_Entidade")
    private Entidade entidade;
    @OneToMany(mappedBy = "recibo")
    private List<LinhaRecibo> linhaRecibos;

    public int getId_Recibo() {
        return id_Recibo;
    }

    public void setId_Recibo(int id_Recibo) {
        this.id_Recibo = id_Recibo;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public double getValor_Final() {
        return valor_Final;
    }

    public void setValor_Final(double valor_Final) {
        this.valor_Final = valor_Final;
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

    public Entidade getEntidade() {
        return entidade;
    }

    public void setEntidade(Entidade entidade) {
        this.entidade = entidade;
    }
}
