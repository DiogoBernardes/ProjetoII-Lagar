package IPVC.DAL;

import jakarta.persistence.*;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.*;

@Entity
@Table(name="Tipo_Produto")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "tipoProduto.index", query = "SELECT tipoProduto FROM TipoProduto tipoProduto"),
        @NamedQuery(name = "tipoProduto.count", query = "SELECT count(tipoProduto) FROM TipoProduto tipoProduto"),
})
public class TipoProduto{
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "Id_TipoProduto")
        private int id_TipoProduto;

        @Basic
        @Column(name = "Descricao", nullable = false)
        private String descricao;

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
}
