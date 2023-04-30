package IPVC.Admin.Provider;

import IPVC.BLL.EntidadeBLL;
import IPVC.DAL.Entidade;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class editProviderController {
    @FXML
    private TextField firstNameTF;
    @FXML
    private TextField emailTF;
    @FXML
    private TextField phoneTF;
    @FXML
    private TextField roadTF;
    @FXML
    private TextField portNumberTF;
    @FXML
    private TextField postalCodeTF;
    @FXML
    private TextField nifTF;
    @FXML
    private Button closeButton;
    @FXML
    private Label Details;

    private Entidade entidade;
    public void setEntidade(Entidade entidade) {
        this.entidade = entidade;
        firstNameTF.setText(entidade.getNome());
        emailTF.setText(entidade.getEmail());
        phoneTF.setText(String.valueOf(entidade.getTelemovel()));
        roadTF.setText(entidade.getRua());
        portNumberTF.setText(String.valueOf(entidade.getN_Porta()));
        postalCodeTF.setText(entidade.getCod_Postal());
        nifTF.setText(String.valueOf(entidade.getNIF()));
    }

    @FXML
    private void updateProvider(ActionEvent event) throws IOException {
        if (entidade != null) {
            entidade.setNome(firstNameTF.getText());
            entidade.setEmail(emailTF.getText());
            entidade.setTelemovel(Integer.parseInt(phoneTF.getText()));
            entidade.setRua(roadTF.getText());
            entidade.setN_Porta(Integer.parseInt(portNumberTF.getText()));
            entidade.setCod_Postal(postalCodeTF.getText());
            entidade.setNIF(Integer.parseInt(nifTF.getText()));
            EntidadeBLL.update(entidade);

            Details.getStyleClass().add("valid-details");
            Details.setText("Cliente editado com Sucesso!");
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
