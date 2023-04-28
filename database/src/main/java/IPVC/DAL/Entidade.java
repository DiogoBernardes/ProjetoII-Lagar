package IPVC.DAL;

import jakarta.persistence.*;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.List;


@Entity
@Table(name="Entidade")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "entidade.index", query = "SELECT entidade FROM Entidade entidade"),
        @NamedQuery(name = "entidade.count", query = "SELECT count(entidade) FROM Entidade entidade"),
        @NamedQuery(name = "entidade.getClients", query = "SELECT entidade FROM Entidade entidade WHERE entidade.tipoEntidade.id_TipoEntidade = :idTipoEntidade"),
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
        @Column(name = "username", nullable = false)
        private String username;
        @Basic
        @Column(name = "password", nullable = false)
        private String password;
        @ManyToOne
        @JoinColumn(name = "Cod_Postal",referencedColumnName = "Cod_Postal")
        private CodigoPostal codigoPostal;
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

    public CodigoPostal getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(CodigoPostal codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public TipoEntidade getTipoEntidade() {
        return tipoEntidade;
    }

    public void setTipoEntidade(TipoEntidade tipoEntidade) {
        this.tipoEntidade = tipoEntidade;
    }
}
