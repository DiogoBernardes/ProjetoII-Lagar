package IPVC.Admin.Provider;

import IPVC.Admin.Client.editClientController;
import IPVC.Admin.Employees.editEmployeesController;
import IPVC.BLL.EntidadeBLL;
import IPVC.BLL.LinhaReciboBLL;
import IPVC.DAL.Entidade;
import IPVC.DAL.LinhaRecibo;
import javafx.beans.Observable;
import javafx.beans.property.SimpleStringProperty;
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
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class providerController {
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
        List<Entidade> clientes = EntidadeBLL.getEntities(1);
        Collections.sort(clientes, Comparator.comparingInt(entidade -> entidade.getId_Entidade()));
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

                String lowerCaseFilter = newValue.toLowerCase().trim();

                if (entidade.getNome().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (String.valueOf(entidade.getNIF()).contains(lowerCaseFilter)) {
                    return true;
                } else if (entidade.getEmail().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (String.valueOf(entidade.getTelemovel()).contains(lowerCaseFilter)) {
                    return true;
                } else if(entidade.getRua().toLowerCase().contains(lowerCaseFilter)){
                    return true;
                } else if(String.valueOf(entidade.getCod_Postal()).contains(lowerCaseFilter)){
                    return true;
                }
                return false;
            });
        });

        // Adiciona o FilteredList à tabela
        dataView.setItems(filteredData);

        // Adiciona um ouvinte à lista data para atualizar a dataView quando ocorrerem alterações
        data.addListener((Observable observable) -> {
            dataView.setItems(data);
            dataView.refresh();
        });
    }
    public void addButtonOnAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/IPVC/views/Admin/Provider/addProvider.fxml"));
        Parent parent = fxmlLoader.load();
        Scene scene = new Scene(parent);
        Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.initOwner(((Node) event.getSource()).getScene().getWindow());
        dialogStage.setTitle("Adicionar Fornecedor");
        dialogStage.setScene(scene);
        dialogStage.showAndWait();

        List<Entidade> clientes = EntidadeBLL.index();
        updateDataView(clientes);
    }
    public void removeButtonOnAction(ActionEvent event) throws IOException {
        Entidade selectedEntidade = dataView.getSelectionModel().getSelectedItem();
        if (selectedEntidade != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Remover fornecedor");
            alert.setHeaderText("Tem a certeza que deseja remover o fornecedor '" + selectedEntidade.getNome() + "'?");

            ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
            ButtonType cancelButton = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(okButton, cancelButton);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == okButton) {
                EntidadeBLL.remove(selectedEntidade.getId_Entidade());

                List<Entidade> clientes = EntidadeBLL.index();
                updateDataView(clientes);

                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setTitle("Remoção bem-sucedida");
                successAlert.setHeaderText(null);
                successAlert.setContentText("O fornecedor foi removido com sucesso!");
                successAlert.showAndWait();
            } else {
                alert.close();
            }
        }else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Remover fornecedor");
            alert.setHeaderText("Para remover um fornecedor é necessário seleciona-lo na tabela!");

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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/IPVC/views/Admin/Provider/editProvider.fxml"));
            Parent parent = loader.load();
            editProviderController controller = loader.getController();
            controller.setEntidade(dataView.getSelectionModel().getSelectedItem());
            Scene scene = new Scene(parent);
            Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.initOwner(((Node) event.getSource()).getScene().getWindow());
            dialogStage.setTitle("Editar Fornecedor");
            dialogStage.setScene(scene);
            dialogStage.showAndWait();
            dataView.refresh();
        }else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Editar fornecedor");
            alert.setHeaderText("Para remover um fornecedor é necessário seleciona-lo na tabela!");

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
    public void clientButtonOnAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/IPVC/views/Admin/Client/clientAdmin.fxml"));
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
    public void productionButtonOnAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/IPVC/views/Admin/Production/productionAdmin.fxml"));
        Scene regCena = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(regCena);
        stage.setTitle("Menu Admin - Produção");
        stage.show();
    }
    public void packagingButtonOnAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/IPVC/views/Admin/Packaging/packagingAdmin.fxml"));
        Scene regCena = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(regCena);
        stage.setTitle("Menu Admin - Embalamento");
        stage.show();
    }
    public void purchaseButtonOnAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/IPVC/views/Admin/Purchase/purchaseAdmin.fxml"));
        Scene regCena = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(regCena);
        stage.setTitle("Menu Admin - Compras");
        stage.show();
    }
    public void salesButtonOnAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/IPVC/views/Admin/Sales/salesAdmin.fxml"));
        Scene regCena = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(regCena);
        stage.setTitle("Menu Admin - Vendas");
        stage.show();
    }
    public void employeesButtonOnAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/IPVC/views/Admin/Employees/employeeAdmin.fxml"));
        Scene regCena = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(regCena);
        stage.setTitle("Menu Admin - Funcionário");
        stage.show();
    }
    private void updateDataView(List<Entidade> clientes) {
        Collections.sort(clientes, Comparator.comparingInt(entidade -> entidade.getId_Entidade()));
        ObservableList<Entidade> data = FXCollections.observableArrayList(clientes);
        dataView.setItems(data);
        dataView.refresh();
    }
}