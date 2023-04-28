package IPVC.DAL;

import jakarta.persistence.*;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.List;

@Entity
@Table(name="Tipo_Entidade")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "tipoEntidade.index", query = "SELECT tipoEntidade FROM TipoEntidade tipoEntidade"),
        @NamedQuery(name = "tipoEntidade.count", query = "SELECT count(tipoEntidade) FROM TipoEntidade tipoEntidade"),
})
public class TipoEntidade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_TipoEntidade")
    private int id_TipoEntidade;

    @Basic
    @Column(name = "Descricao", nullable = false)
    private String descricao;

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
}
