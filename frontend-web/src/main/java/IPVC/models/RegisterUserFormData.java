package IPVC.models;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class RegisterUserFormData {

    @NotBlank
    public String nome;

    @NotBlank
    public String NIF;

    @NotBlank
    public String Rua;

    @NotBlank
    public String num_porta;

    @NotBlank
    public String cod_postal;

    @NotBlank
    public String telemovel;

    @NotBlank
    @Email
    public String email;

    @NotBlank
    public String username;

    @NotBlank
    public String password;

    @NotBlank
    public String confirmPassword;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNIF() {
        return NIF;
    }

    public void setNIF(String NIF) {
        this.NIF = NIF;
    }

    public String getRua() {
        return Rua;
    }

    public void setRua(String rua) {
        Rua = rua;
    }

    public String getNum_porta() {
        return num_porta;
    }

    public void setNum_porta(String num_porta) {
        this.num_porta = num_porta;
    }

    public String getCod_postal() {
        return cod_postal;
    }

    public void setCod_postal(String cod_postal) {
        this.cod_postal = cod_postal;
    }

    public String getTelemovel() {
        return telemovel;
    }

    public void setTelemovel(String telemovel) {
        this.telemovel = telemovel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
