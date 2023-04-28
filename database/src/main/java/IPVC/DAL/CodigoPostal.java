package IPVC.DAL;

import jakarta.persistence.*;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.List;

@Entity
@Table(name="Codigo_Postal")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "cp.index", query = "SELECT cp FROM CodigoPostal cp"),
        @NamedQuery(name = "cp.count", query = "SELECT count(cp) FROM CodigoPostal cp"),
})
public class CodigoPostal {

    @Id
    @Column(name = "Cod_Postal")
    private String Cod_Postal;

    @Basic
    @Column(name = "Localidade", nullable = false)
    private String localidade;

    @OneToMany(mappedBy = "codigoPostal")
    private List<Entidade> entidades;
    public CodigoPostal() {}

    public CodigoPostal(String id){
        this.Cod_Postal = id;
    }

    @Override
    public String toString() {
        return String.format("Codigo Postal[cp='%s']", Cod_Postal);
    }

    public String getCod_Postal() {
        return Cod_Postal;
    }

    public void setCod_Postal(String cod_Postal) {
        Cod_Postal = cod_Postal;
    }

    public String getLocalidade() {
        return localidade;
    }

    public void setLocalidade(String localidade) {
        this.localidade = localidade;
    }
}
