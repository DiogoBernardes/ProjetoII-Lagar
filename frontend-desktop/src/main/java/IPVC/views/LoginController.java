package IPVC.views;
import IPVC.BLL.UtilizadorBLL;
import IPVC.DAL.Utilizador;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.animation.PauseTransition;
import javafx.util.Duration;
import java.io.IOException;

public class LoginController {

    @FXML
    private Button loginButton;
    @FXML
    private PasswordField passwordTextField;
    @FXML
    private TextField usernameTextField;
    @FXML
    private Label invalidDetails;

    public void initialize() {
        loginButton.getStyleClass().add("login-button");
    }


    public void loginButtonOnAction(ActionEvent event) throws IOException {
        String user = usernameTextField.getText();
        String pass = passwordTextField.getText();

        if (user.isEmpty() || pass.isEmpty()) {
            invalidDetails.getStyleClass().add("invalid-details-error");
            invalidDetails.setText("Os dados de login são necessários!");
            usernameTextField.getStyleClass().add("TF-EmptyLogin");
            passwordTextField.getStyleClass().add("TF-EmptyLogin");
            PauseTransition pause = new PauseTransition(Duration.seconds(1.5));
            pause.setOnFinished(e -> {
                invalidDetails.setText("");
                invalidDetails.getStyleClass().removeAll("invalid-details-error");
                usernameTextField.getStyleClass().removeAll("TF-EmptyLogin");
                passwordTextField.getStyleClass().removeAll("TF-EmptyLogin");
            });
            pause.play();
        } else if (UtilizadorBLL.checkLogin(user, pass)) {
            Utilizador utilizador = UtilizadorBLL.getDataLogin(user, pass);
            if (utilizador.getTipoUtilizador().getId_TipoUtilizador() == 1) {
                Utilizador currentUser = utilizador;
                Session.getInstance().setCurrentUser(currentUser);
                currentUser = utilizador;
                Parent root = FXMLLoader.load(getClass().getResource("Admin/mAdmin.fxml"));
                Scene regCena = new Scene(root);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(regCena);
                stage.setTitle("Menu Admin");
                stage.show();
            } else if (utilizador.getTipoUtilizador().getId_TipoUtilizador() == 2) {
                //Menu Gestor
                Utilizador currentUser = utilizador;
                Session.getInstance().setCurrentUser(currentUser);
                currentUser = utilizador;
                Parent root = FXMLLoader.load(getClass().getResource("Gestor/mGestor.fxml"));
                Scene regCena = new Scene(root);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(regCena);
                stage.setTitle("Menu Gestor");
                stage.show();
            } else if (utilizador.getTipoUtilizador().getId_TipoUtilizador() == 3) {
                //Menu Funcionário
                Utilizador currentUser = utilizador;
                Session.getInstance().setCurrentUser(currentUser);
                currentUser = utilizador;
                Parent root = FXMLLoader.load(getClass().getResource("Funcionario/mFuncionario.fxml"));
                Scene regCena = new Scene(root);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(regCena);
                stage.setTitle("Menu Funcionário");
                stage.show();
            }
        } else {
            invalidDetails.getStyleClass().add("invalid-details-error");
            invalidDetails.setText("Dados Incorretos!");
            usernameTextField.getStyleClass().add("TF-EmptyLogin");
            passwordTextField.getStyleClass().add("TF-EmptyLogin");
            PauseTransition pause = new PauseTransition(Duration.seconds(1.5));
            pause.setOnFinished(e -> {
                invalidDetails.setText("");
                invalidDetails.getStyleClass().removeAll("invalid-details-error");
                usernameTextField.getStyleClass().removeAll("TF-EmptyLogin");
                passwordTextField.getStyleClass().removeAll("TF-EmptyLogin");
            });
            pause.play();
        }
    }

}