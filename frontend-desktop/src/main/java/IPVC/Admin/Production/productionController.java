package IPVC.Admin.Production;

import IPVC.Admin.Purchase.editPurchaseController;
import IPVC.BLL.FaturaBLL;
import IPVC.BLL.LinhaFaturaBLL;
import IPVC.BLL.ProducaoBLL;
import IPVC.BLL.ProdutoMPBLL;
import IPVC.DAL.LinhaFatura;
import IPVC.DAL.Producao;
import IPVC.DAL.ProdutoMP;
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
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;

public class productionController {
    @FXML
    private TableView<ProdutoMP> dataView;
    @FXML
    private TableColumn<ProdutoMP, String> producaoColumn;
    @FXML
    private TableColumn<ProdutoMP, String> produtoMPColumn;
    @FXML
    private TableColumn<ProdutoMP, String> quantidadeColumn;
    @FXML
    private TableColumn<ProdutoMP, String> produtoFColumn;
    @FXML
    private TableColumn<ProdutoMP, String> qtdProdColumn;
    @FXML
    private TableColumn<ProdutoMP, String> acidezColumn;
    @FXML
    private TableColumn<ProdutoMP, String> dataColumn;
    @FXML
    private TextField searchTF;
    @FXML
    private void initialize() {
        List<ProdutoMP> produtoMPS = ProdutoMPBLL.index();

        ObservableList<ProdutoMP> data = FXCollections.observableArrayList(produtoMPS);
        //dataView.setItems(data);
        dataView.setItems(data);
        producaoColumn.setCellValueFactory(d -> new SimpleStringProperty(String.valueOf(d.getValue().getProducao().getId_Producao())));
        produtoMPColumn.setCellValueFactory(d -> new SimpleStringProperty(String.valueOf(d.getValue().getProduto().getNome())));
        quantidadeColumn.setCellValueFactory(d -> new SimpleStringProperty(String.valueOf(d.getValue().getQuantidade())));
        produtoFColumn.setCellValueFactory(d -> new SimpleStringProperty(String.valueOf(d.getValue().getProducao().getProduto().getNome())));
        qtdProdColumn.setCellValueFactory(d -> new SimpleStringProperty(String.valueOf(d.getValue().getProducao().getQtd_Produzida())));
        acidezColumn.setCellValueFactory(d -> new SimpleStringProperty(String.valueOf(d.getValue().getProducao().getAcidez())));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        dataColumn.setCellValueFactory(d -> new SimpleStringProperty(String.valueOf(sdf.format(d.getValue().getProducao().getData()))));
        dataView.getSortOrder().add(producaoColumn);
        // Cria um FilteredList com a lista de clientes
        FilteredList<ProdutoMP> filteredData = new FilteredList<>(data, p -> true);

        // Adiciona um listener ao TextField de busca para filtrar a tabela quando o texto mudar
        searchTF.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(producao -> {
                // Se o texto de busca estiver vazio, exibe todos os clientes
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Converte o texto de busca em minúsculas e remove espaços no início e no final
                String lowerCaseFilter = newValue.toLowerCase().trim();

                // Filtra o cliente se o nome, o NIF, o email ou o número de telefone contiverem o texto de busca
                if (String.valueOf(producao.getProducao().getId_Producao()).contains(lowerCaseFilter)) {
                    return true; // O nome contém o texto de busca
                } else if (String.valueOf(producao.getProduto().getNome()).contains(lowerCaseFilter)) {
                    return true; // O produtoMP contém o texto de busca
                } else if (String.valueOf(producao.getProducao().getProduto().getNome()).contains(lowerCaseFilter)){
                    return true; // O produtoFinal contém o texto de busca
                } else if (String.valueOf(producao.getProducao().getAcidez()).contains(lowerCaseFilter)) {
                    return true; // A acidez contém o texto de busca
                } else if(String.valueOf(producao.getProducao().getData()).contains(lowerCaseFilter)){
                    return true; // A rua contém o texto de busca
                }
                return false; // Não há correspondência
            });
        });
        // Adiciona o FilteredList à tabela
        dataView.setItems(filteredData);
    }

    public void addButtonOnAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/IPVC/views/Admin/Production/addProduction.fxml"));
        Parent parent = fxmlLoader.load();
        Scene scene = new Scene(parent);
        Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.initOwner(((Node) event.getSource()).getScene().getWindow());
        dialogStage.setTitle("Adicionar Produção");
        dialogStage.setScene(scene);
        dialogStage.showAndWait();
    }

    public void removeButtonOnAction(ActionEvent event) throws IOException {
        ProdutoMP selectedprodutoMP = dataView.getSelectionModel().getSelectedItem();
        if (selectedprodutoMP != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Remover Produção");
            alert.setHeaderText("Tem a certeza que deseja remover a produção '" + selectedprodutoMP.getProducao().getId_Producao() + "'?");

            ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
            ButtonType cancelButton = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(okButton, cancelButton);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == okButton) {
                ProdutoMPBLL.removeByProducao(selectedprodutoMP.getProducao().getId_Producao());
                ProducaoBLL.remove( selectedprodutoMP.getProducao().getId_Producao());
                dataView.getItems().remove(selectedprodutoMP);
            } else {
                alert.close();
            }
        }else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Remover Produção");
            alert.setHeaderText("Para remover uma produção é necessário seleciona-la na tabela!");

            ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);

            alert.getButtonTypes().setAll(okButton);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == okButton) {
                alert.close();
            }
    }
    }

    public void editButtonOnAction(ActionEvent event) throws IOException {
        ProdutoMP selectedprodutoMP = dataView.getSelectionModel().getSelectedItem();
        if (selectedprodutoMP != null) {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/IPVC/views/Admin/Production/editProduction.fxml"));
            Parent root = loader.load();
            editProductionController controller = loader.getController();
            Producao selectedProducao = selectedprodutoMP.getProducao();
            controller.setProduction(selectedprodutoMP, selectedProducao);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        }else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Editar Produção");
            alert.setHeaderText("Para editar uma produção é necessário seleciona-lo na tabela!");

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
    public void purchaseButtonOnAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/IPVC/views/Admin/Purchase/purchaseAdmin.fxml"));
        Scene regCena = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(regCena);
        stage.setTitle("Menu Admin - Compras");
        stage.show();
    }
}