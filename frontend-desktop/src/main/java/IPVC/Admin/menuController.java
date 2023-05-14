package IPVC.Admin;

import IPVC.DAL.Utilizador;
import IPVC.views.Session;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class menuController {
    private final Utilizador currentUser = Session.getInstance().getCurrentUser();

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