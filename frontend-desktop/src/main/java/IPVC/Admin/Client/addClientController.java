package IPVC.Admin.Client;

import IPVC.BLL.EntidadeBLL;
import IPVC.BLL.TipoEntidadeBLL;
import IPVC.DAL.Entidade;
import IPVC.DAL.TipoEntidade;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class addClientController {

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


    public void addClientButtonOnAction(ActionEvent event) throws IOException {
        if (!firstNameTF.getText().isEmpty() || !emailTF.getText().isEmpty() || !phoneTF.getText().isEmpty() ||
                !roadTF.getText().isEmpty() || !portNumberTF.getText().isEmpty() || !postalCodeTF.getText().isEmpty() || !nifTF.getText().isEmpty()) {

            boolean emailNotExist = EntidadeBLL.checkEmail(emailTF.getText());
            boolean phoneNotExist = EntidadeBLL.checkTelemovel(phoneTF.getText());
            boolean nifNotExist = EntidadeBLL.checkNIF(nifTF.getText());

            if (!emailNotExist) {
                Details.getStyleClass().add("invalid-details-error");
                Details.setText("O e-mail inserido já está a ser utilizado!");
                emailTF.getStyleClass().add("TF-EmptyLogin");
                PauseTransition pause = new PauseTransition(Duration.seconds(1.5));
                pause.setOnFinished(e -> {
                    Details.setText("");
                    Details.getStyleClass().removeAll("invalid-details-error");
                    emailTF.getStyleClass().removeAll("TF-EmptyLogin");
                });
                pause.play();

            } else if (!phoneNotExist) {
                Details.getStyleClass().add("invalid-details-error");
                Details.setText("O número de telefone inserido já está a ser utilizado!");
                phoneTF.getStyleClass().add("TF-EmptyLogin");
                PauseTransition pause = new PauseTransition(Duration.seconds(1.5));
                pause.setOnFinished(e -> {
                    Details.setText("");
                    Details.getStyleClass().removeAll("invalid-details-error");
                    phoneTF.getStyleClass().removeAll("TF-EmptyLogin");
                });
                pause.play();

            } else if (!nifNotExist) {
                Details.getStyleClass().add("invalid-details-error");
                Details.setText("O NIF inserido já está a ser utilizado!");
                nifTF.getStyleClass().add("TF-EmptyLogin");
                PauseTransition pause = new PauseTransition(Duration.seconds(1.5));
                pause.setOnFinished(e -> {
                    Details.setText("");
                    Details.getStyleClass().removeAll("invalid-details-error");
                    nifTF.getStyleClass().removeAll("TF-EmptyLogin");
                });
                pause.play();

            } else if (emailNotExist && phoneNotExist && nifNotExist) {
                Entidade newClient = new Entidade();
                newClient.setNome(firstNameTF.getText());
                newClient.setEmail(emailTF.getText());
                newClient.setTelemovel(Integer.parseInt(phoneTF.getText()));
                newClient.setRua(roadTF.getText());
                newClient.setN_Porta(Integer.parseInt(portNumberTF.getText()));
                newClient.setCod_Postal(postalCodeTF.getText());
                newClient.setNIF(Integer.parseInt(nifTF.getText()));
                TipoEntidade tipoEntidade = TipoEntidadeBLL.get(2);
                newClient.setTipoEntidade(tipoEntidade);
                EntidadeBLL.create(newClient);

                Details.getStyleClass().add("valid-details");
                Details.setText("Cliente inserido com Sucesso!");
                firstNameTF.setText("");
                emailTF.setText("");
                phoneTF.setText("");
                roadTF.setText("");
                portNumberTF.setText("");
                postalCodeTF.setText("");
                nifTF.setText("");

                PauseTransition pause = new PauseTransition(Duration.seconds(1.5));
                pause.setOnFinished(e -> {
                    Details.setText("");
                    Details.getStyleClass().removeAll("invalid-details-error");
                    nifTF.getStyleClass().removeAll("TF-EmptyLogin");
                });
                pause.play();
            }
        } else {
                Details.getStyleClass().add("invalid-details-error");
                Details.setText("Os dados do cliente são necessários!");
                firstNameTF.getStyleClass().add("TF-EmptyLogin");
                emailTF.getStyleClass().add("TF-EmptyLogin");
                phoneTF.getStyleClass().add("TF-EmptyLogin");
                roadTF.getStyleClass().add("TF-EmptyLogin");
                portNumberTF.getStyleClass().add("TF-EmptyLogin");
                postalCodeTF.getStyleClass().add("TF-EmptyLogin");
                nifTF.getStyleClass().add("TF-EmptyLogin");

                PauseTransition pause = new PauseTransition(Duration.seconds(1.5));
                pause.setOnFinished(e -> {
                    Details.setText("");
                    Details.getStyleClass().removeAll("invalid-details-error");
                    firstNameTF.getStyleClass().removeAll("TF-EmptyLogin");
                    emailTF.getStyleClass().removeAll("TF-EmptyLogin");
                    phoneTF.getStyleClass().removeAll("TF-EmptyLogin");
                    roadTF.getStyleClass().removeAll("TF-EmptyLogin");
                    portNumberTF.getStyleClass().removeAll("TF-EmptyLogin");
                    postalCodeTF.getStyleClass().removeAll("TF-EmptyLogin");
                    nifTF.getStyleClass().removeAll("TF-EmptyLogin");
                });
                pause.play();
            }

    }

    public void closeButtonOnAction(ActionEvent event) throws IOException {
        Stage dialogStage = (Stage) closeButton.getScene().getWindow();
        dialogStage.close();
    }

}
