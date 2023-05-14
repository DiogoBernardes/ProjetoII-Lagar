package IPVC.Admin.Packaging;

import IPVC.Admin.Employees.editEmployeesController;
import IPVC.Admin.Production.editProductionController;
import IPVC.BLL.*;
import IPVC.DAL.*;
import IPVC.views.Session;
import javafx.beans.Observable;
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
import java.util.Collections;
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

    private final Utilizador currentUser = Session.getInstance().getCurrentUser();
    @FXML
    private void initialize() throws IOException {
        List<ProdutoEMB> produtoEMBS = ProdutoEMBBLL.index();
        Collections.sort(produtoEMBS, Comparator.comparingInt(produtoEMB -> produtoEMB.getEmbalamento().getId_Embalamento()));
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

        // Adiciona um ouvinte à lista data para atualizar a dataView quando ocorrerem alterações
        data.addListener((Observable observable) -> {
            dataView.setItems(data);
            dataView.refresh();
        });
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

                List<ProdutoEMB> produtoEMBS = ProdutoEMBBLL.index();
                updateDataView(produtoEMBS);
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

        List<ProdutoEMB> produtoEMBS = ProdutoEMBBLL.index();
        updateDataView(produtoEMBS);
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

                List<ProdutoEMB> produtoEMBS = ProdutoEMBBLL.index();
                updateDataView(produtoEMBS);

                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setTitle("Remoção bem-sucedida");
                successAlert.setHeaderText(null);
                successAlert.setContentText("O embalamento foi removido com sucesso!");
                successAlert.showAndWait();
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
            Parent parent = loader.load();
            editPackagingController controller = loader.getController();
            Embalamento selectedEmbalamento = selectedprodutoEMB.getEmbalamento();
            controller.setPackaging(selectedprodutoEMB, selectedEmbalamento);
            Scene scene = new Scene(parent);
            Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.initOwner(((Node) event.getSource()).getScene().getWindow());
            dialogStage.setTitle("Editar Embalamento");
            dialogStage.setScene(scene);
            dialogStage.showAndWait();
            dataView.refresh();
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
        if(currentUser.getTipoUtilizador().getId_TipoUtilizador() == 1){
            Parent root = FXMLLoader.load(getClass().getResource("/IPVC/views/Admin/mAdmin.fxml"));
            Scene regCena = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(regCena);
            stage.setTitle("Menu Admin");
            stage.show();
        }else if(currentUser.getTipoUtilizador().getId_TipoUtilizador() == 2){
            Parent root = FXMLLoader.load(getClass().getResource("/IPVC/views/Gestor/mGestor.fxml"));
            Scene regCena = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(regCena);
            stage.setTitle("Menu Gestor ");
            stage.show();
        }else if(currentUser.getTipoUtilizador().getId_TipoUtilizador() == 3){
            Parent root = FXMLLoader.load(getClass().getResource("/IPVC/views/Funcionario/mFuncionario.fxml"));
            Scene regCena = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(regCena);
            stage.setTitle("Menu Funcionário");
            stage.show();
        }
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
    public void clientButtonOnAction(ActionEvent event) throws IOException {
        if(currentUser.getTipoUtilizador().getId_TipoUtilizador() == 1){
            Parent root = FXMLLoader.load(getClass().getResource("/IPVC/views/Admin/Client/clientAdmin.fxml"));
            Scene regCena = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(regCena);
            stage.setTitle("Menu Admin - Cliente");
            stage.show();
        }else if(currentUser.getTipoUtilizador().getId_TipoUtilizador() == 2){
            Parent root = FXMLLoader.load(getClass().getResource("/IPVC/views/Gestor/Client/clientGestor.fxml"));
            Scene regCena = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(regCena);
            stage.setTitle("Menu Gestor - Cliente");
            stage.show();
        }
    }
    public void providerButtonOnAction(ActionEvent event) throws IOException {
        if(currentUser.getTipoUtilizador().getId_TipoUtilizador() == 1){
            Parent root = FXMLLoader.load(getClass().getResource("/IPVC/views/Admin/Provider/providerAdmin.fxml"));
            Scene regCena = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(regCena);
            stage.setTitle("Menu Admin - Fornecedores");
            stage.show();
        }else if(currentUser.getTipoUtilizador().getId_TipoUtilizador() == 2){
            Parent root = FXMLLoader.load(getClass().getResource("/IPVC/views/Gestor/Provider/providerGestor.fxml"));
            Scene regCena = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(regCena);
            stage.setTitle("Menu Gestor - Fornecedores");
            stage.show();
        }

    }
    public void productButtonOnAction(ActionEvent event) throws IOException {
        if(currentUser.getTipoUtilizador().getId_TipoUtilizador() == 1) {
            Parent root = FXMLLoader.load(getClass().getResource("/IPVC/views/Admin/Product/productAdmin.fxml"));
            Scene regCena = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(regCena);
            stage.setTitle("Menu Admin - Produtos");
            stage.show();
        }else if(currentUser.getTipoUtilizador().getId_TipoUtilizador() == 2){
            Parent root = FXMLLoader.load(getClass().getResource("/IPVC/views/Gestor/Product/productGestor.fxml"));
            Scene regCena = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(regCena);
            stage.setTitle("Menu Gestor - Produtos");
            stage.show();
        }else if(currentUser.getTipoUtilizador().getId_TipoUtilizador() == 3){
            Parent root = FXMLLoader.load(getClass().getResource("/IPVC/views/Funcionario/Product/productFuncionario.fxml"));
            Scene regCena = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(regCena);
            stage.setTitle("Menu Funcionário - Produtos");
            stage.show();
        }
    }
    public void productionButtonOnAction(ActionEvent event) throws IOException {
        if(currentUser.getTipoUtilizador().getId_TipoUtilizador() == 1) {
            Parent root = FXMLLoader.load(getClass().getResource("/IPVC/views/Admin/Production/productionAdmin.fxml"));
            Scene regCena = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(regCena);
            stage.setTitle("Menu Admin - Produção");
            stage.show();
        }else if(currentUser.getTipoUtilizador().getId_TipoUtilizador() == 2){
            Parent root = FXMLLoader.load(getClass().getResource("/IPVC/views/Gestor/Production/productionGestor.fxml"));
            Scene regCena = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(regCena);
            stage.setTitle("Menu Gestor - Produção");
            stage.show();
        }else if(currentUser.getTipoUtilizador().getId_TipoUtilizador() == 3){
            Parent root = FXMLLoader.load(getClass().getResource("/IPVC/views/Funcionario/Production/productionFuncionario.fxml"));
            Scene regCena = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(regCena);
            stage.setTitle("Menu Funcionário - Produção");
            stage.show();
        }
    }
    public void purchaseButtonOnAction(ActionEvent event) throws IOException {
        if(currentUser.getTipoUtilizador().getId_TipoUtilizador() == 1) {
            Parent root = FXMLLoader.load(getClass().getResource("/IPVC/views/Admin/Purchase/purchaseAdmin.fxml"));
            Scene regCena = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(regCena);
            stage.setTitle("Menu Admin - Compras");
            stage.show();
        }else if(currentUser.getTipoUtilizador().getId_TipoUtilizador() == 2){
            Parent root = FXMLLoader.load(getClass().getResource("/IPVC/views/Gestor/Purchase/purchaseGestor.fxml"));
            Scene regCena = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(regCena);
            stage.setTitle("Menu Gestor - Compras");
            stage.show();
        }
    }
    public void salesButtonOnAction(ActionEvent event) throws IOException {
        if(currentUser.getTipoUtilizador().getId_TipoUtilizador() == 1) {
            Parent root = FXMLLoader.load(getClass().getResource("/IPVC/views/Admin/Sales/salesAdmin.fxml"));
            Scene regCena = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(regCena);
            stage.setTitle("Menu Admin - Vendas");
            stage.show();
        }else if(currentUser.getTipoUtilizador().getId_TipoUtilizador() == 2){
            Parent root = FXMLLoader.load(getClass().getResource("/IPVC/views/Gestor/Sales/salesGestor.fxml"));
            Scene regCena = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(regCena);
            stage.setTitle("Menu Gestor - Vendas");
            stage.show();
        }
    }
    public void employeesButtonOnAction(ActionEvent event) throws IOException {
        if(currentUser.getTipoUtilizador().getId_TipoUtilizador() == 1) {
            Parent root = FXMLLoader.load(getClass().getResource("/IPVC/views/Admin/Employees/employeeAdmin.fxml"));
            Scene regCena = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(regCena);
            stage.setTitle("Menu Admin - Funcionário");
            stage.show();
        }else if(currentUser.getTipoUtilizador().getId_TipoUtilizador() == 2){
            Parent root = FXMLLoader.load(getClass().getResource("/IPVC/views/Gestor/Employees/employeeGestor.fxml"));
            Scene regCena = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(regCena);
            stage.setTitle("Menu Gestor - Funcionário");
            stage.show();
        }
    }
    private void updateDataView(List<ProdutoEMB> produtoEMBS) {
        Collections.sort(produtoEMBS, Comparator.comparingInt(produtoEMB -> produtoEMB.getEmbalamento().getId_Embalamento()));
        ObservableList<ProdutoEMB> data = FXCollections.observableArrayList(produtoEMBS);
        dataView.setItems(data);
        dataView.refresh();
    }
}