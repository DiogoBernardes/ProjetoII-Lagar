package IPVC.DAL;

import jakarta.persistence.*;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name="Tipo_Pagamento")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "tipoPagamento.index", query = "SELECT tipoPagamento FROM TipoPagamento tipoPagamento WHERE tipoPagamento.deleted_on = null"),
        @NamedQuery(name = "tipoPagamento.count", query = "SELECT count(tipoPagamento) FROM TipoPagamento tipoPagamento WHERE tipoPagamento.deleted_on = null"),
        @NamedQuery(name = "tipoPagamento.getPaymentByDescription", query = "SELECT tipoPagamento FROM TipoPagamento tipoPagamento WHERE tipoPagamento.descricao = :descricao and tipoPagamento.deleted_on = null"),
})
public class TipoPagamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_TipoPagamento")
    private int id_TipoPagamento;

    @Basic
    @Column(name = "Descricao", nullable = false)
    private String descricao;
    @Basic
    @Column(name = "deleted_on")
    private Timestamp deleted_on;

    @OneToMany(mappedBy = "tipoPagamento")
    private List<Fatura> faturas;
    @OneToMany(mappedBy = "tipoPagamento")
    private List<Recibo> recibos;

    public int getId_TipoPagamento() {
        return id_TipoPagamento;
    }

    public void setId_TipoPagamento(int id_TipoPagamento) {
        this.id_TipoPagamento = id_TipoPagamento;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Timestamp getDeleted_on() {
        return deleted_on;
    }

    public void setDeleted_on(Timestamp deleted_on) {
        this.deleted_on = deleted_on;
    }
}
