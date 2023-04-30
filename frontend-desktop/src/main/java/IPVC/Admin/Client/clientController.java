package IPVC.Admin.Client;

import IPVC.BLL.EntidadeBLL;
import IPVC.DAL.Entidade;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class clientController {
    @FXML
    private TableView<Entidade> dataView;
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
    private TextField searchTF;
    @FXML
    private void initialize() {
        List<Entidade> clientes = EntidadeBLL.getClients(2);

        // Cria um ObservableList com os clientes filtrados e atualiza a tabela
        ObservableList<Entidade> data = FXCollections.observableArrayList(clientes);

        dataView.setItems(data);
        nomeColumn.setCellValueFactory(new PropertyValueFactory<>("nome"));
        NIFColumn.setCellValueFactory(d -> new SimpleStringProperty(String.valueOf(d.getValue().getNIF())));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        telemovelColumn.setCellValueFactory(d -> new SimpleStringProperty(String.valueOf(d.getValue().getTelemovel())));
        ruaColumn.setCellValueFactory(new PropertyValueFactory<>("rua"));
        numPortaColumn.setCellValueFactory(d -> new SimpleStringProperty(String.valueOf(d.getValue().getN_Porta())));
        cpColumn.setCellValueFactory(d -> new SimpleStringProperty(String.valueOf(d.getValue().getCod_Postal())));

        // Cria um FilteredList com a lista de clientes
        FilteredList<Entidade> filteredData = new FilteredList<>(data, p -> true);

        // Adiciona um listener ao TextField de busca para filtrar a tabela quando o texto mudar
        searchTF.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(entidade -> {
                // Se o texto de busca estiver vazio, exibe todos os clientes
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Converte o texto de busca em minúsculas e remove espaços no início e no final
                String lowerCaseFilter = newValue.toLowerCase().trim();

                // Filtra o cliente se o nome, o NIF, o email ou o número de telefone contiverem o texto de busca
                if (entidade.getNome().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // O nome contém o texto de busca
                } else if (String.valueOf(entidade.getNIF()).contains(lowerCaseFilter)) {
                    return true; // O NIF contém o texto de busca
                } else if (entidade.getEmail().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // O email contém o texto de busca
                } else if (String.valueOf(entidade.getTelemovel()).contains(lowerCaseFilter)) {
                    return true; // O número de telefone contém o texto de busca
                } else if(entidade.getRua().toLowerCase().contains(lowerCaseFilter)){
                    return true; // A rua contém o texto de busca
                } else if(String.valueOf(entidade.getCod_Postal()).contains(lowerCaseFilter)){
                    return true; // O codigo de Postal contém o texto de busca
                }
                return false; // Não há correspondência
            });
        });

        // Adiciona o FilteredList à tabela
        dataView.setItems(filteredData);
    }

    public void addButtonOnAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/IPVC/views/Admin/Client/addClient.fxml"));
        Parent parent = fxmlLoader.load();
        Scene scene = new Scene(parent);
        Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.initOwner(((Node) event.getSource()).getScene().getWindow());
        dialogStage.setTitle("Adicionar Cliente");
        dialogStage.setScene(scene);
        dialogStage.showAndWait();
    }
    public void removeButtonOnAction(ActionEvent event) throws IOException {
        Entidade selectedEntidade = dataView.getSelectionModel().getSelectedItem();
        if (selectedEntidade != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Remover Cliente");
            alert.setHeaderText("Tem a certeza que deseja remover o cliente '" + selectedEntidade.getNome() + "'?");

            ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
            ButtonType cancelButton = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(okButton, cancelButton);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == okButton) {
                EntidadeBLL.remove(selectedEntidade.getId_Entidade());
                dataView.getItems().remove(selectedEntidade);
            } else {
                alert.close();
            }
        }else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Editar Cliente");
            alert.setHeaderText("Para remover um cliente é necessário seleciona-lo na tabela!");

            ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);

            alert.getButtonTypes().setAll(okButton);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == okButton) {
                alert.close();
            }
    }
    }
    public void editButtonOnAction(ActionEvent event) throws IOException {
        Entidade selectedEntidade = dataView.getSelectionModel().getSelectedItem();
        if (selectedEntidade != null) {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/IPVC/views/Admin/Client/editClient.fxml"));
            Parent root = loader.load();
            editClientController controller = loader.getController();
            controller.setEntidade(dataView.getSelectionModel().getSelectedItem());
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        }else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Editar Cliente");
            alert.setHeaderText("Para remover um cliente é necessário seleciona-lo na tabela!");

            ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);

            alert.getButtonTypes().setAll(okButton);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == okButton) {
                alert.close();
            }
        }
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
    public void providerButtonOnAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/IPVC/views/Admin/Provider/providerAdmin.fxml"));
        Scene regCena = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(regCena);
        stage.setTitle("Menu Admin - Clientes");
        stage.show();
    }
    public void productButtonOnAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/IPVC/views/Admin/Product/productAdmin.fxml"));
        Scene regCena = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(regCena);
        stage.setTitle("Menu Admin - Produtos");
        stage.show();
    }
}