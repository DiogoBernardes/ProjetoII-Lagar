package IPVC.DAL;

import jakarta.persistence.*;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.sql.Timestamp;
import java.util.List;


@Entity
@Table(name="Entidade")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "entidade.index", query = "SELECT entidade FROM Entidade entidade WHERE entidade.deleted_on = null"),
        @NamedQuery(name = "entidade.count", query = "SELECT count(entidade) FROM Entidade entidade WHERE entidade.deleted_on = null"),
        @NamedQuery(name = "entidade.getClients", query = "SELECT entidade FROM Entidade entidade WHERE entidade.tipoEntidade.id_TipoEntidade = :idTipoEntidade and entidade.deleted_on = null"),
        @NamedQuery(name = "entidade.getNIF", query = "SELECT entidade FROM Entidade entidade WHERE entidade.NIF = :NIF and entidade.deleted_on = null"),
        @NamedQuery(name = "entidade.getTelemovel", query = "SELECT entidade FROM Entidade entidade WHERE entidade.Telemovel = :Telemovel and entidade.deleted_on = null"),
        @NamedQuery(name = "entidade.getEmail", query = "SELECT entidade FROM Entidade entidade WHERE entidade.Email = :Email and entidade.deleted_on = null"),
        @NamedQuery(name = "entidade.getEntityByName", query = "SELECT entidade FROM Entidade entidade WHERE entidade.Nome = :Nome and entidade.deleted_on = null"),

})
public class Entidade {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "Id_Entidade")
        private int Id_Entidade;
        @Basic
        @Column(name = "Nome", nullable = false)
        private String Nome;
        @Basic
        @Column(name = "Email", nullable = false)
        private String Email;
        @Basic
        @Column(name = "NIF", nullable = false)
        private int NIF;
        @Basic
        @Column(name = "Telemovel", nullable = false)
        private int Telemovel;
        @Basic
        @Column(name = "Rua", nullable = false)
        private String Rua;
        @Basic
        @Column(name = "N_Porta", nullable = false)
        private int N_Porta;
        @Basic
        @Column(name = "Cod_Postal", nullable = false)
        private String Cod_Postal;
        @Basic
        @Column(name = "deleted_on")
        private Timestamp deleted_on;
        @ManyToOne
        @JoinColumn(name = "Id_TipoEntidade",referencedColumnName = "Id_TipoEntidade")
        private TipoEntidade tipoEntidade;
        @OneToMany(mappedBy = "entidade")
        private List<Fatura> faturas;
        @OneToMany(mappedBy = "entidade")
        private List<Recibo> recibos;



    public int getId_Entidade() {
        return Id_Entidade;
    }

    public void setId_Entidade(int id_Entidade) {
        Id_Entidade = id_Entidade;
    }

    public String getNome() {
        return Nome;
    }

    public void setNome(String nome) {
        Nome = nome;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public int getNIF() {
        return NIF;
    }

    public void setNIF(int NIF) {
        this.NIF = NIF;
    }

    public int getTelemovel() {
        return Telemovel;
    }

    public void setTelemovel(int telemovel) {
        Telemovel = telemovel;
    }

    public String getRua() {
        return Rua;
    }

    public void setRua(String rua) {
        Rua = rua;
    }

    public int getN_Porta() {
        return N_Porta;
    }

    public void setN_Porta(int n_Porta) {
        N_Porta = n_Porta;
    }

    public String getCod_Postal() {
        return Cod_Postal;
    }

    public void setCod_Postal(String cod_Postal) {
        Cod_Postal = cod_Postal;
    }

    public TipoEntidade getTipoEntidade() {
        return tipoEntidade;
    }

    public void setTipoEntidade(TipoEntidade tipoEntidade) {
        this.tipoEntidade = tipoEntidade;
    }

    public Timestamp getDeleted_on() {
        return deleted_on;
    }

    public void setDeleted_on(Timestamp deleted_on) {
        this.deleted_on = deleted_on;
    }
}
