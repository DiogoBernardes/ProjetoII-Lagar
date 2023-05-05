package IPVC.DAL;

import jakarta.persistence.*;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name="Utilizador")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "utilizador.index", query = "SELECT utilizador FROM Utilizador utilizador WHERE utilizador.deleted_on = null "),
        @NamedQuery(name = "utilizador.count", query = "SELECT count(utilizador) FROM Utilizador utilizador"),
        @NamedQuery(name = "utilizador.getDataLogin", query = "SELECT utilizador FROM Utilizador utilizador WHERE utilizador.username = :username AND utilizador.password = :pass AND utilizador.deleted_on = null"),
        @NamedQuery(name = "utilizador.getTelemovel", query = "SELECT utilizador FROM Utilizador utilizador WHERE utilizador.telemovel = :Telemovel AND utilizador.deleted_on = null"),
        @NamedQuery(name = "utilizador.getUsername", query = "SELECT utilizador FROM Utilizador utilizador WHERE utilizador.username = :username AND utilizador.deleted_on = null"),
        @NamedQuery(name = "utilizador.getByRole", query = "SELECT utilizador FROM Utilizador utilizador WHERE utilizador.tipoUtilizador.cargo = :cargo AND utilizador.deleted_on = null"),
})
public class Utilizador {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "Id_Utilizador")
        private int id_Utilizador;
        @Basic
        @Column(name = "Nome", nullable = false)
        private String nome;
        @Basic
        @Column(name = "Telemovel", nullable = false)
        private int telemovel;
        @Basic
        @Column(name = "username", nullable = false)
        private String username;
        @Basic
        @Column(name = "password", nullable = false)
        private String password;
        @Basic
        @Column(name = "deleted_on")
        private Timestamp deleted_on;
        @ManyToOne
        @JoinColumn(name = "Id_TipoUtilizador",referencedColumnName = "Id_TipoUtilizador")
        private TipoUtilizador tipoUtilizador;
        @OneToMany(mappedBy = "utilizador")
        private List<Fatura> faturas;


    public int getId_Utilizador() {
        return id_Utilizador;
    }

    public void setId_Utilizador(int id_Utilizador) {
        this.id_Utilizador = id_Utilizador;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getTelemovel() {
        return telemovel;
    }

    public void setTelemovel(int telemovel) {
        this.telemovel = telemovel;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public TipoUtilizador getTipoUtilizador() {
        return tipoUtilizador;
    }

    public void setTipoUtilizador(TipoUtilizador tipoUtilizador) {
        this.tipoUtilizador = tipoUtilizador;
    }

    public Timestamp getDeleted_on() {
        return deleted_on;
    }

    public void setDeleted_on(Timestamp deleted_on) {
        this.deleted_on = deleted_on;
    }
}
