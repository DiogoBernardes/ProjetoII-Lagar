package IPVC.Admin.Packaging;

import IPVC.Admin.Production.editProductionController;
import IPVC.BLL.EmbalamentoBLL;
import IPVC.BLL.ProducaoBLL;
import IPVC.BLL.ProdutoEMBBLL;
import IPVC.BLL.ProdutoMPBLL;
import IPVC.DAL.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class packagingController {
    @FXML
    private TableView<ProdutoEMB> dataView;
    @FXML
    private TableColumn<ProdutoEMB, String> embalamentoColumn;
    @FXML
    private TableColumn<ProdutoEMB, String> produtoEMBColumn;
    @FXML
    private TableColumn<ProdutoEMB, String> quantidadeColumn;
    @FXML
    private TableColumn<ProdutoEMB, String> produtoFColumn;
    @FXML
    private TableColumn<ProdutoEMB, String> qtdProdColumn;
    @FXML
    private TableColumn<ProdutoEMB, String> dataColumn;
    @FXML
    private TextField searchTF;

    @FXML
    private void initialize() throws IOException {
        List<ProdutoEMB> produtoEMBS = ProdutoEMBBLL.index();
        List<Embalamento> embalamentos = EmbalamentoBLL.index();
        embalamentos.sort(Comparator.comparingInt(Embalamento::getId_Embalamento));
        ObservableList<ProdutoEMB> data = FXCollections.observableArrayList(produtoEMBS);
        dataView.setItems(data);
        embalamentoColumn.setCellValueFactory(d -> new SimpleStringProperty(String.valueOf(d.getValue().getEmbalamento().getId_Embalamento())));
        produtoEMBColumn.setCellValueFactory(d -> new SimpleStringProperty(String.valueOf(d.getValue().getProduto().getNome())));
        quantidadeColumn.setCellValueFactory(d -> new SimpleStringProperty(String.valueOf(d.getValue().getQuantidade())));
        produtoFColumn.setCellValueFactory(d -> new SimpleStringProperty(String.valueOf(d.getValue().getEmbalamento().getProduto().getNome())));
        qtdProdColumn.setCellValueFactory(d -> new SimpleStringProperty(String.valueOf(d.getValue().getEmbalamento().getQtd_Embalada())));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        dataColumn.setCellValueFactory(d -> new SimpleStringProperty(String.valueOf(sdf.format(d.getValue().getEmbalamento().getData()))));
        dataView.getSortOrder().add(embalamentoColumn);

        // Cria um FilteredList com a lista de produtosEmbalados
        FilteredList<ProdutoEMB> filteredData = new FilteredList<>(data, p -> true);
        // Adiciona um listener ao TextField de busca para filtrar a tabela quando o texto mudar
        searchTF.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(embalamento -> {
                // Se o texto de busca estiver vazio, exibe todos os clientes
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Converte o texto de busca em minúsculas e remove espaços no início e no final
                String lowerCaseFilter = newValue.toLowerCase().trim();

                if (String.valueOf(embalamento.getEmbalamento().getId_Embalamento()).contains(lowerCaseFilter)) {
                    return true;
                } else if (String.valueOf(embalamento.getProduto().getNome()).contains(lowerCaseFilter)) {
                    return true;
                } else if (String.valueOf(embalamento.getEmbalamento().getProduto().getNome()).contains(lowerCaseFilter)){
                    return true;
                } else if(String.valueOf(embalamento.getEmbalamento().getData()).contains(lowerCaseFilter)){
                    return true;
                }
                return false;
            });
        });
        // Adiciona o FilteredList à tabela
        dataView.setItems(filteredData);

    }

    public void addButtonOnAction(ActionEvent event) throws IOException {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/IPVC/views/Admin/Packaging/addPackaging.fxml"));
                Parent parent = fxmlLoader.load();
                Scene scene = new Scene(parent);
                Stage dialogStage = new Stage();
                dialogStage.initModality(Modality.APPLICATION_MODAL);
                dialogStage.initOwner(((Node) event.getSource()).getScene().getWindow());
                dialogStage.setTitle("Adicionar Embalamento");
                dialogStage.setScene(scene);
                dialogStage.showAndWait();
    }

    public void addMoreProductsButtonOnAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/IPVC/views/Admin/Packaging/addMorePackaging.fxml"));
        Parent parent = fxmlLoader.load();
        Scene scene = new Scene(parent);
        Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.initOwner(((Node) event.getSource()).getScene().getWindow());
        dialogStage.setTitle("Adicionar Produto a um Embalamento");
        dialogStage.setScene(scene);
        dialogStage.showAndWait();
    }
    public void removeButtonOnAction(ActionEvent event) throws IOException {
        ProdutoEMB selectedprodutoEMB = dataView.getSelectionModel().getSelectedItem();
        if (selectedprodutoEMB != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Remover Embalamento");
            alert.setHeaderText("Tem a certeza que deseja remover o embalamento '" + selectedprodutoEMB.getEmbalamento().getId_Embalamento() + "'?");

            ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
            ButtonType cancelButton = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(okButton, cancelButton);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == okButton) {
                ProdutoEMBBLL.removeByEmbalamento(selectedprodutoEMB.getEmbalamento().getId_Embalamento());
                EmbalamentoBLL.remove(selectedprodutoEMB.getEmbalamento().getId_Embalamento());
                dataView.getItems().remove(selectedprodutoEMB);
            } else {
                alert.close();
            }
        }else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Remover Embalamento");
            alert.setHeaderText("Para remover um embalamento é necessário seleciona-lo na tabela!");

            ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);

            alert.getButtonTypes().setAll(okButton);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == okButton) {
                alert.close();
            }
    }
    }

   public void editButtonOnAction(ActionEvent event) throws IOException {
        ProdutoEMB selectedprodutoEMB = dataView.getSelectionModel().getSelectedItem();
        if (selectedprodutoEMB != null) {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/IPVC/views/Admin/Packaging/editPackaging.fxml"));
            Parent root = loader.load();
            editPackagingController controller = loader.getController();
            Embalamento selectedEmbalamento = selectedprodutoEMB.getEmbalamento();
            controller.setPackaging(selectedprodutoEMB, selectedEmbalamento);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        }else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Editar Embalamento");
            alert.setHeaderText("Para editar um embalamento é necessário seleciona-lo na tabela!");

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
    public void productionButtonOnAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/IPVC/views/Admin/Production/productionAdmin.fxml"));
        Scene regCena = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(regCena);
        stage.setTitle("Menu Admin - Produção");
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