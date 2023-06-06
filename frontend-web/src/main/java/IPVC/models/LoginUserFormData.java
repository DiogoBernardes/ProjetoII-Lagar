package IPVC.models;
import javax.validation.constraints.*;

public class LoginUserFormData {


    @NotBlank
    public String username;

    @NotBlank
    public String password;


    public String getUsername() {
        return username;
    }

    public void setUsername(String email) {
        this.username = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
