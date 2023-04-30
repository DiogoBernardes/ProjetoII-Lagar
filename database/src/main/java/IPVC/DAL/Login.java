package IPVC.DAL;

import jakarta.persistence.*;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.List;
@Entity
@Table(name="Login")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "login.index", query = "SELECT login FROM Login login"),
        @NamedQuery(name = "login.count", query = "SELECT count(login) FROM Login login"),
})
public class Login {

        @Id
        @Column(name = "Login")
        private int Login;

        @Basic
        @Column(name = "Username", nullable = false)
        private String username;
        @Basic
        @Column(name = "Password", nullable = false)
        private String password;

        @ManyToOne
        @JoinColumn(name = "Entidade",referencedColumnName = "Id_Entidade")
        private Entidade entidade;


        public Login() {}

    public int getLogin() {
        return Login;
    }

    public void setLogin(int login) {
        Login = login;
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

    public Entidade getEntidade() {
        return entidade;
    }

    public void setEntidade(Entidade entidade) {
        this.entidade = entidade;
    }
}
