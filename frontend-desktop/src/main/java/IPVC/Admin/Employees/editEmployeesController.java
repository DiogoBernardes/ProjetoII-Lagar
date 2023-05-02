package IPVC.Admin.Employees;

import IPVC.BLL.ProdutoBLL;
import IPVC.BLL.TipoUtilizadorBLL;
import IPVC.BLL.UtilizadorBLL;
import IPVC.DAL.Produto;
import IPVC.DAL.TipoUtilizador;
import IPVC.DAL.Utilizador;
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

public class editEmployeesController {
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

    private Utilizador utilizador;

    public void setUtilizador(Utilizador utilizadores) {
        this.utilizador = utilizadores;

        List<TipoUtilizador> tipoUtilizadors = TipoUtilizadorBLL.index();

        ObservableList<String> tUtilizador = FXCollections.observableArrayList();

        for (TipoUtilizador tp : tipoUtilizadors) {
            tUtilizador.add(tp.getCargo());
        }

        nomeTF.setText(utilizadores.getNome());
        telemovelTF.setText(String.valueOf(utilizadores.getTelemovel()));
        usernameTF.setText(utilizadores.getUsername());
        passwordPF.setText(utilizadores.getPassword());
        tipoUtilizadorCB.setItems(tUtilizador);
        tipoUtilizadorCB.setValue(utilizadores.getTipoUtilizador().getCargo());
    }

    @FXML
    private void updateEmployees(ActionEvent event) throws IOException {
        if (utilizador != null) {

            String tipoUtilizadorCargo = tipoUtilizadorCB.getSelectionModel().getSelectedItem();
            TipoUtilizador tipo_utilizador = TipoUtilizadorBLL.getEmployeeByRole(tipoUtilizadorCargo);

            utilizador.setNome(nomeTF.getText());
            utilizador.setTelemovel(Integer.parseInt(telemovelTF.getText()));
            utilizador.setUsername(usernameTF.getText());
            utilizador.setPassword(passwordPF.getText());
            utilizador.setTipoUtilizador(tipo_utilizador);


            UtilizadorBLL.update(utilizador);

            Details.getStyleClass().add("valid-details");
            Details.setText("Utilizador editado com Sucesso!");
            PauseTransition pause = new PauseTransition(Duration.seconds(1.5));
            pause.setOnFinished(e -> {
                Details.setText("");
                Details.getStyleClass().removeAll("invalid-details-error");
            });
            pause.play();
        }
    }

    public void closeButtonOnAction(ActionEvent event) throws IOException {
        Stage dialogStage = (Stage) closeButton.getScene().getWindow();
        dialogStage.close();
    }


}
