package IPVC.Admin.Employees;

import IPVC.BLL.*;
import IPVC.DAL.*;
import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.List;

public class addEmployeesController {

    @FXML
    private TextField nomeTF;
    @FXML
    private TextField telemovelTF;
    @FXML
    private TextField usernameTF;
    @FXML
    private PasswordField passwordPF;
    @FXML
    private ComboBox<String> tipoUtilizadorCB;
    @FXML
    private Button closeButton;
    @FXML
    private Label Details;

    @FXML
    private void initialize() {
        List<TipoUtilizador> tipoUtilizadors = TipoUtilizadorBLL.index();

        ObservableList<String> tUtilizador = FXCollections.observableArrayList();

        for (TipoUtilizador tp : tipoUtilizadors) {
            tUtilizador.add(tp.getCargo());
        }

        tipoUtilizadorCB.setItems(tUtilizador);
    }

    public void addEmployeesButtonOnAction(ActionEvent event) throws IOException {
        if (!nomeTF.getText().isEmpty() || !telemovelTF.getText().isEmpty() || !passwordPF.getText().isEmpty() ||
                !tipoUtilizadorCB.getValue().isEmpty()) {

            boolean usernameNotExist = UtilizadorBLL.checkUsername(usernameTF.getText());
            boolean phoneNotExist = UtilizadorBLL.checkTelemovel(telemovelTF.getText());


            if (!usernameNotExist) {
                Details.getStyleClass().add("invalid-details-error");
                Details.setText("O username inserido já está a ser utilizado!");
                usernameTF.getStyleClass().add("TF-EmptyLogin");
                PauseTransition pause = new PauseTransition(Duration.seconds(1.5));
                pause.setOnFinished(e -> {
                    Details.setText("");
                    Details.getStyleClass().removeAll("invalid-details-error");
                    usernameTF.getStyleClass().removeAll("TF-EmptyLogin");
                });
                pause.play();

            } else if (!phoneNotExist) {
                Details.getStyleClass().add("invalid-details-error");
                Details.setText("O número de telemóvel inserido já está a ser utilizado!");
                telemovelTF.getStyleClass().add("TF-EmptyLogin");
                PauseTransition pause = new PauseTransition(Duration.seconds(1.5));
                pause.setOnFinished(e -> {
                    Details.setText("");
                    Details.getStyleClass().removeAll("invalid-details-error");
                    telemovelTF.getStyleClass().removeAll("TF-EmptyLogin");
                });
                pause.play();

            } else if (usernameNotExist && phoneNotExist) {
                Utilizador newUtilizador = new Utilizador();
                newUtilizador.setNome(nomeTF.getText());
                newUtilizador.setTelemovel(Integer.parseInt(telemovelTF.getText()));
                newUtilizador.setUsername(usernameTF.getText());
                newUtilizador.setPassword(passwordPF.getText());
                String tipoUtilizadorDescricao = tipoUtilizadorCB.getSelectionModel().getSelectedItem();
                TipoUtilizador tipoUtilizador = TipoUtilizadorBLL.getEmployeeByRole(tipoUtilizadorDescricao);
                newUtilizador.setTipoUtilizador(tipoUtilizador);

                UtilizadorBLL.create(newUtilizador);

                Details.getStyleClass().add("valid-details");
                Details.setText("Utilizador inserido com Sucesso!");
                nomeTF.setText("");
                telemovelTF.setText("");
                usernameTF.setText("");
                passwordPF.setText("");
                tipoUtilizadorCB.setValue(null);

                PauseTransition pause = new PauseTransition(Duration.seconds(1.5));
                pause.setOnFinished(e -> {
                    Details.setText("");
                    Details.getStyleClass().removeAll("invalid-details-error");
                });
                pause.play();
            }
        } else {
                Details.getStyleClass().add("invalid-details-error");
                Details.setText("Os dados do utilizador são necessários!");
                nomeTF.getStyleClass().add("TF-EmptyLogin");
                telemovelTF.getStyleClass().add("TF-EmptyLogin");
                usernameTF.getStyleClass().add("TF-EmptyLogin");
                passwordPF.getStyleClass().add("TF-EmptyLogin");
                tipoUtilizadorCB.getStyleClass().add("TF-EmptyLogin");

                PauseTransition pause = new PauseTransition(Duration.seconds(1.5));
                pause.setOnFinished(e -> {
                    Details.setText("");
                    Details.getStyleClass().removeAll("invalid-details-error");
                    nomeTF.getStyleClass().removeAll("TF-EmptyLogin");
                    telemovelTF.getStyleClass().removeAll("TF-EmptyLogin");
                    usernameTF.getStyleClass().removeAll("TF-EmptyLogin");
                    passwordPF.getStyleClass().removeAll("TF-EmptyLogin");
                    tipoUtilizadorCB.getStyleClass().removeAll("TF-EmptyLogin");
                });
                pause.play();
            }

    }

    public void closeButtonOnAction(ActionEvent event) throws IOException {
        Stage dialogStage = (Stage) closeButton.getScene().getWindow();
        dialogStage.close();
    }

}
