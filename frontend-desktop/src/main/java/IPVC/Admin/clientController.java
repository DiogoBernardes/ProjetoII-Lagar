package IPVC.Admin;

import IPVC.BLL.EntidadeBLL;
import IPVC.DAL.Entidade;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class clientController {
    @FXML
    private TableView<Entidade> clientView;
    @FXML
    private TableColumn<Entidade, String> nomeColumn;
    @FXML
    private TableColumn<Entidade, String> NIFColumn;
    @FXML
    private TableColumn<Entidade, String> emailColumn;
    @FXML
    private TableColumn<Entidade, String> telemovelColumn;
    @FXML
    private TableColumn<Entidade, String> ruaColumn;
    @FXML
    private TableColumn<Entidade, String> numPortaColumn;
    @FXML
    private TableColumn<Entidade, String> cpColumn;

    @FXML
    private void initialize() {
        // Obtém a lista de clientes usando o método getClient da classe EntidadeBLL
        List<Entidade> clientes = EntidadeBLL.getClients(2);

        // Cria um ObservableList com os clientes filtrados e atualiza a tabela
        ObservableList<Entidade> data = FXCollections.observableArrayList(clientes);
        clientView.setItems(data);

        // Configura as colunas da tabela
        nomeColumn.setCellValueFactory(new PropertyValueFactory<>("nome"));
        NIFColumn.setCellValueFactory(d -> new SimpleStringProperty(String.valueOf(d.getValue().getNIF())));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        telemovelColumn.setCellValueFactory(d -> new SimpleStringProperty(String.valueOf(d.getValue().getTelemovel())));
        ruaColumn.setCellValueFactory(new PropertyValueFactory<>("rua"));
        numPortaColumn.setCellValueFactory(d -> new SimpleStringProperty(String.valueOf(d.getValue().getN_Porta())));
        cpColumn.setCellValueFactory(d -> new SimpleStringProperty(String.valueOf(d.getValue().getCodigoPostal().getCod_Postal())));


    }
    public void backButtonOnAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/IPVC/views/Admin/mAdmin.fxml"));
        Scene regCena = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(regCena);
        stage.setTitle("Menu Admin");
        stage.show();
    }
    public void logoutButtonOnAction(ActionEvent event) throws IOException {
        ButtonType confirmButtonType = new ButtonType("Confirmar", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButtonType = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Tem certeza que deseja terminar sessão?", confirmButtonType, cancelButtonType);
        alert.setTitle("Confirmação");
        alert.setHeaderText(null);
        DialogPane alertDialog = alert.getDialogPane();
        alertDialog.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
        alertDialog.getStyleClass().add("dialog");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == confirmButtonType) {
            // usuário confirmou a saída, execute a ação de logout
            Parent root = FXMLLoader.load(getClass().getResource("/IPVC/views/Login.fxml"));
            Scene regCena = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(regCena);
            stage.setTitle("Login");
            stage.show();
        }
    }
}