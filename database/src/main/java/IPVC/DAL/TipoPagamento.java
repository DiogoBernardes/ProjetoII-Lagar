package IPVC.DAL;

import jakarta.persistence.*;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.List;

@Entity
@Table(name="Tipo_Pagamento")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "tipoPagamento.index", query = "SELECT tipoPagamento FROM TipoPagamento tipoPagamento"),
        @NamedQuery(name = "tipoPagamento.count", query = "SELECT count(tipoPagamento) FROM TipoPagamento tipoPagamento"),
        @NamedQuery(name = "tipoPagamento.getPaymentByDescription", query = "SELECT tipoPagamento FROM TipoPagamento tipoPagamento WHERE tipoPagamento.descricao = :descricao"),
})
public class TipoPagamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_TipoPagamento")
    private int id_TipoPagamento;

    @Basic
    @Column(name = "Descricao", nullable = false)
    private String descricao;

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
}
