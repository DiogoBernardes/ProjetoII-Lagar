package IPVC.DAL;

import jakarta.persistence.*;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name="Tipo_Entidade")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "tipoEntidade.index", query = "SELECT tipoEntidade FROM TipoEntidade tipoEntidade WHERE tipoEntidade.deleted_on = null"),
        @NamedQuery(name = "tipoEntidade.count", query = "SELECT count(tipoEntidade) FROM TipoEntidade tipoEntidade WHERE tipoEntidade.deleted_on = null"),
})
public class TipoEntidade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_TipoEntidade")
    private int id_TipoEntidade;

    @Basic
    @Column(name = "Descricao", nullable = false)
    private String descricao;
    @Basic
    @Column(name = "deleted_on")
    private Timestamp deleted_on;

    @OneToMany(mappedBy = "tipoEntidade")
    private List<Entidade> entidades;

    public int getId_TipoEntidade() {
        return id_TipoEntidade;
    }

    public void setId_TipoEntidade(int id_TipoEntidade) {
        this.id_TipoEntidade = id_TipoEntidade;
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
