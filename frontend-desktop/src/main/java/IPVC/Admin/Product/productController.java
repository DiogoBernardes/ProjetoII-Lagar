package IPVC.Admin.Product;

import IPVC.Admin.Employees.editEmployeesController;
import IPVC.Admin.Product.editProductController;
import IPVC.BLL.LinhaReciboBLL;
import IPVC.BLL.ProdutoBLL;
import IPVC.DAL.LinhaRecibo;
import IPVC.DAL.Produto;
import IPVC.DAL.ProdutoMP;
import IPVC.DAL.Utilizador;
import IPVC.views.Session;
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

public class productController {
    @FXML
    private TableView<Produto> dataView;
    @FXML
    private TableColumn<Produto, String> nomeColumn;
    @FXML
    private TableColumn<Produto, String> idColumn;
    @FXML
    private TableColumn<Produto, String> valorColumn;
    @FXML
    private TableColumn<Produto, String> quantidadeColumn;
    @FXML
    private TableColumn<Produto, String> unidadeColumn;
    @FXML
    private TableColumn<Produto, String> tipoProdutoColumn;
    @FXML
    private TextField searchTF;

    private final Utilizador currentUser = Session.getInstance().getCurrentUser();
    @FXML
    private void initialize() {
        List<Produto> produtos = ProdutoBLL.index();
        Collections.sort(produtos, Comparator.comparingInt(produto -> produto.getId_Produto()));
        ObservableList<Produto> data = FXCollections.observableArrayList(produtos);

        dataView.setItems(data);
        nomeColumn.setCellValueFactory(new PropertyValueFactory<>("nome"));
        idColumn.setCellValueFactory(d -> new SimpleStringProperty(String.valueOf(d.getValue().getId_Produto())));
        valorColumn.setCellValueFactory(d -> new SimpleStringProperty(String.valueOf(d.getValue().getValor_Unitario())));
        quantidadeColumn.setCellValueFactory(d -> new SimpleStringProperty(String.valueOf(d.getValue().getQuantidade())));
        unidadeColumn.setCellValueFactory(new PropertyValueFactory<>("unidade"));
        tipoProdutoColumn.setCellValueFactory(d ->new SimpleStringProperty(String.valueOf(d.getValue().getTipoProduto().getDescricao())));

        // Cria um FilteredList com a lista de clientes
        FilteredList<Produto> filteredData = new FilteredList<>(data, p -> true);

        // Adiciona um listener ao TextField de busca para filtrar a tabela quando o texto mudar
        searchTF.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(produto -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase().trim();

                if (produto.getNome().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (String.valueOf(produto.getId_Produto()).contains(lowerCaseFilter)) {
                    return true;
                } else if (String.valueOf(produto.getValor_Unitario()).contains(lowerCaseFilter)) {
                    return true;
                }else if (String.valueOf(produto.getTipoProduto().getDescricao()).contains(lowerCaseFilter)) {
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
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/IPVC/views/Admin/Product/addProduct.fxml"));
        Parent parent = fxmlLoader.load();
        Scene scene = new Scene(parent);
        Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.initOwner(((Node) event.getSource()).getScene().getWindow());
        dialogStage.setTitle("Adicionar Produto");
        dialogStage.setScene(scene);
        dialogStage.showAndWait();

        List<Produto> produtos = ProdutoBLL.index();
        updateDataView(produtos);
    }
    public void removeButtonOnAction(ActionEvent event) throws IOException {
        Produto selectedProduto = dataView.getSelectionModel().getSelectedItem();
        if (selectedProduto != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Remover produto");
            alert.setHeaderText("Tem a certeza que deseja remover o produto '" + selectedProduto.getNome() + "'?");

            ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
            ButtonType cancelButton = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(okButton, cancelButton);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == okButton) {
                ProdutoBLL.remove(selectedProduto.getId_Produto());

                List<Produto> produtos = ProdutoBLL.index();
                updateDataView(produtos);

                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setTitle("Remoção bem-sucedida");
                successAlert.setHeaderText(null);
                successAlert.setContentText("O produto foi removido com sucesso!");
                successAlert.showAndWait();
            } else {
                alert.close();
            }
        }else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Remover produto");
            alert.setHeaderText("Para remover um produto é necessário seleciona-lo na tabela!");

            ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);

            alert.getButtonTypes().setAll(okButton);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == okButton) {
                alert.close();
            }
    }
    }
    public void editButtonOnAction(ActionEvent event) throws IOException {
        Produto selectedProduto = dataView.getSelectionModel().getSelectedItem();
        if (selectedProduto != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/IPVC/views/Admin/Product/editProduct.fxml"));
            Parent parent = loader.load();
            editProductController controller = loader.getController();
            controller.setProduct(dataView.getSelectionModel().getSelectedItem());
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
            stage.show();
        }
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
    public void packagingButtonOnAction(ActionEvent event) throws IOException {
        if(currentUser.getTipoUtilizador().getId_TipoUtilizador() == 1) {
            Parent root = FXMLLoader.load(getClass().getResource("/IPVC/views/Admin/Packaging/packagingAdmin.fxml"));
            Scene regCena = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(regCena);
            stage.setTitle("Menu Admin - Embalamento");
            stage.show();
        }else if(currentUser.getTipoUtilizador().getId_TipoUtilizador() == 2){
            Parent root = FXMLLoader.load(getClass().getResource("/IPVC/views/Gestor/Packaging/packagingGestor.fxml"));
            Scene regCena = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(regCena);
            stage.setTitle("Menu Gestor - Embalamento");
            stage.show();
        }else if(currentUser.getTipoUtilizador().getId_TipoUtilizador() == 3){
            Parent root = FXMLLoader.load(getClass().getResource("/IPVC/views/Funcionario/Packaging/packagingFuncionario.fxml"));
            Scene regCena = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(regCena);
            stage.setTitle("Menu Funcionário - Embalamento");
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
    private void updateDataView(List<Produto> produtos) {
        Collections.sort(produtos, Comparator.comparingInt(produto -> produto.getId_Produto()));
        ObservableList<Produto> data = FXCollections.observableArrayList(produtos);
        dataView.setItems(data);
        dataView.refresh();
    }

}