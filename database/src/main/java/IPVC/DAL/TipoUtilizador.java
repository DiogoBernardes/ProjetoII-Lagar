package IPVC.DAL;

import jakarta.persistence.*;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name="Tipo_Utilizador")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "tipoUtilizador.index", query = "SELECT tipoUtilizador FROM TipoUtilizador tipoUtilizador WHERE tipoUtilizador.deleted_on = null"),
        @NamedQuery(name = "tipoUtilizador.count", query = "SELECT count(tipoUtilizador) FROM TipoUtilizador tipoUtilizador WHERE tipoUtilizador.deleted_on = null"),
        @NamedQuery(name = "tipoUtilizador.getEmployeeByRole", query = "SELECT tipoUtilizador FROM TipoUtilizador tipoUtilizador WHERE tipoUtilizador.cargo = :Cargo and tipoUtilizador.deleted_on = null"),

})
public class TipoUtilizador {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "Id_TipoUtilizador")
        private int id_TipoUtilizador;

        @Basic
        @Column(name = "Cargo", nullable = false)
        private String cargo;

        @Basic
        @Column(name = "deleted_on")
        private Timestamp deleted_on;
        @OneToMany(mappedBy = "tipoUtilizador")
        private List<Utilizador> utilizadores;

    public int getId_TipoUtilizador() {
        return id_TipoUtilizador;
    }

    public void setId_TipoUtilizador(int id_TipoUtilizador) {
        this.id_TipoUtilizador = id_TipoUtilizador;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public Timestamp getDeleted_on() {
        return deleted_on;
    }

    public void setDeleted_on(Timestamp deleted_on) {
        this.deleted_on = deleted_on;
    }
}


