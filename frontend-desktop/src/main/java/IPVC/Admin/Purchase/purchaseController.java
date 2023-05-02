package IPVC.Admin.Purchase;

import IPVC.Admin.Client.editClientController;
import IPVC.BLL.EntidadeBLL;
import IPVC.BLL.FaturaBLL;
import IPVC.BLL.LinhaFaturaBLL;
import IPVC.DAL.Entidade;
import IPVC.DAL.Fatura;
import IPVC.DAL.LinhaFatura;
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
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;

public class purchaseController {
    @FXML
    private TableView<LinhaFatura> dataView;
    @FXML
    private TableColumn<LinhaFatura, String> faturaColumn;
    @FXML
    private TableColumn<LinhaFatura, String> produtoColumn;
    @FXML
    private TableColumn<LinhaFatura, String> quantidadeColumn;
    @FXML
    private TableColumn<LinhaFatura, String> valorColumn;
    @FXML
    private TableColumn<LinhaFatura, String> valorFinalColumn;
    @FXML
    private TableColumn<LinhaFatura, String> fornecedorColumn;
    @FXML
    private TableColumn<LinhaFatura, String> utilizadorColumn;
    @FXML
    private TableColumn<LinhaFatura, String> pagamentoColumn;
    @FXML
    private TableColumn<LinhaFatura, String> dataColumn;
    @FXML
    private TextField searchTF;
    @FXML
    private void initialize() {
        List<LinhaFatura> linha_faturas = LinhaFaturaBLL.index();

        ObservableList<LinhaFatura> data = FXCollections.observableArrayList(linha_faturas);

        dataView.setItems(data);
        faturaColumn.setCellValueFactory(d -> new SimpleStringProperty(String.valueOf(d.getValue().getFatura().getId_Fatura())));
        produtoColumn.setCellValueFactory(d -> new SimpleStringProperty(String.valueOf(d.getValue().getProduto().getNome())));
        quantidadeColumn.setCellValueFactory(d -> new SimpleStringProperty(String.valueOf(d.getValue().getQuantidade())));
        valorColumn.setCellValueFactory(d -> new SimpleStringProperty(String.valueOf(d.getValue().getValor())));
        valorFinalColumn.setCellValueFactory(d -> new SimpleStringProperty(String.valueOf(d.getValue().getFatura().getValor_Total())));
        fornecedorColumn.setCellValueFactory(d -> new SimpleStringProperty(String.valueOf(d.getValue().getFatura().getEntidade().getNome())));
        utilizadorColumn.setCellValueFactory(d -> new SimpleStringProperty(String.valueOf(d.getValue().getFatura().getUtilizador().getNome())));
        pagamentoColumn.setCellValueFactory(d -> new SimpleStringProperty(String.valueOf(d.getValue().getFatura().getTipoPagamento().getDescricao())));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        dataColumn.setCellValueFactory(d -> new SimpleStringProperty(String.valueOf(sdf.format(d.getValue().getFatura().getData()))));

        // Cria um FilteredList com a lista de clientes
        FilteredList<LinhaFatura> filteredData = new FilteredList<>(data, p -> true);

        // Adiciona um listener ao TextField de busca para filtrar a tabela quando o texto mudar
        searchTF.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(fatura -> {
                // Se o texto de busca estiver vazio, exibe todos os clientes
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Converte o texto de busca em minúsculas e remove espaços no início e no final
                String lowerCaseFilter = newValue.toLowerCase().trim();

                // Filtra o cliente se o nome, o NIF, o email ou o número de telefone contiverem o texto de busca
                if (String.valueOf(fatura.getFatura().getId_Fatura()).contains(lowerCaseFilter)) {
                    return true; // O nome contém o texto de busca
                } else if (String.valueOf(fatura.getProduto().getNome()).contains(lowerCaseFilter)) {
                    return true; // O NIF contém o texto de busca
                } else if (fatura.getFatura().getEntidade().getNome().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // O email contém o texto de busca
                } else if (String.valueOf(fatura.getFatura().getData()).contains(lowerCaseFilter)) {
                    return true; // O número de telefone contém o texto de busca
                } else if(fatura.getFatura().getUtilizador().getNome().toLowerCase().contains(lowerCaseFilter)){
                    return true; // A rua contém o texto de busca
                } else if(String.valueOf(fatura.getFatura().getValor_Total()).contains(lowerCaseFilter)){
                    return true; // O codigo de Postal contém o texto de busca
                }
                return false; // Não há correspondência
            });
        });

        // Adiciona o FilteredList à tabela
        dataView.setItems(filteredData);
    }

    public void addButtonOnAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/IPVC/views/Admin/Purchase/addPurchase.fxml"));
        Parent parent = fxmlLoader.load();
        Scene scene = new Scene(parent);
        Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.initOwner(((Node) event.getSource()).getScene().getWindow());
        dialogStage.setTitle("Adicionar Fatura");
        dialogStage.setScene(scene);
        dialogStage.showAndWait();
    }
    public void removeButtonOnAction(ActionEvent event) throws IOException {
        LinhaFatura selectedFatura = dataView.getSelectionModel().getSelectedItem();
        if (selectedFatura != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Remover fatura");
            alert.setHeaderText("Tem a certeza que deseja remover a fatura '" + selectedFatura.getFatura().getId_Fatura() + "'?");

            ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
            ButtonType cancelButton = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(okButton, cancelButton);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == okButton) {
                LinhaFaturaBLL.removeByFatura(selectedFatura.getFatura().getId_Fatura());
                FaturaBLL.remove(selectedFatura.getFatura().getId_Fatura());
                dataView.getItems().remove(selectedFatura);
            } else {
                alert.close();
            }
        }else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Remover Fatura");
            alert.setHeaderText("Para remover uma fatura é necessário seleciona-la na tabela!");

            ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);

            alert.getButtonTypes().setAll(okButton);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == okButton) {
                alert.close();
            }
    }
    }
    public void editButtonOnAction(ActionEvent event) throws IOException {
        LinhaFatura selectedLinhaFatura = dataView.getSelectionModel().getSelectedItem();
        if (selectedLinhaFatura != null) {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/IPVC/views/Admin/Purchase/editPurchase.fxml"));
            Parent root = loader.load();
            editPurchaseController controller = loader.getController();
            Fatura selectedFatura = selectedLinhaFatura.getFatura();
            controller.setPurchase(selectedLinhaFatura,selectedFatura);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        }else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Editar Fatura");
            alert.setHeaderText("Para remover uma fatura é necessário seleciona-lo na tabela!");

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
            stage.show();        }
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
    public void clientButtonOnAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/IPVC/views/Admin/Client/clientAdmin.fxml"));
        Scene regCena = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(regCena);
        stage.setTitle("Menu Admin - Faturas");
        stage.show();
    }
}