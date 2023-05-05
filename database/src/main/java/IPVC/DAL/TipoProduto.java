package IPVC.DAL;

import jakarta.persistence.*;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.sql.Timestamp;
import java.util.*;

@Entity
@Table(name="Tipo_Produto")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "tipoProduto.index", query = "SELECT tipoProduto FROM TipoProduto tipoProduto WHERE tipoProduto.deleted_on = null"),
        @NamedQuery(name = "tipoProduto.count", query = "SELECT count(tipoProduto) FROM TipoProduto tipoProduto WHERE tipoProduto.deleted_on = null"),
        @NamedQuery(name = "tipoProduto.getByDescription", query = "SELECT tipoProduto FROM TipoProduto tipoProduto WHERE tipoProduto.descricao = :descricao and tipoProduto.deleted_on = null"),
})
public class TipoProduto{
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "Id_TipoProduto")
        private int id_TipoProduto;

        @Basic
        @Column(name = "Descricao", nullable = false)
        private String descricao;
        @Basic
        @Column(name = "deleted_on")
        private Timestamp deleted_on;
        @OneToMany(mappedBy = "tipoProduto")
        private List<Produto> produtos;



    public int getId_TipoProduto() {
        return id_TipoProduto;
    }

    public void setId_TipoProduto(int id_TipoProduto) {
        this.id_TipoProduto = id_TipoProduto;
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
