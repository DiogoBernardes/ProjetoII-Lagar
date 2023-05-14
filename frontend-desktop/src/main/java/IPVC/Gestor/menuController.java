package IPVC.Gestor;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class menuController {


    public void clientButtonOnAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/IPVC/views/Gestor/Client/clientGestor.fxml"));
        Scene regCena = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(regCena);
        stage.setTitle("Menu Gestor - Clientes");
        stage.show();
    }
    public void providerButtonOnAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/IPVC/views/Gestor/Provider/providerGestor.fxml"));
        Scene regCena = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(regCena);
        stage.setTitle("Menu Gestor - Fornecedor");
        stage.show();
    }
    public void productButtonOnAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/IPVC/views/Gestor/Product/productGestor.fxml"));
        Scene regCena = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(regCena);
        stage.setTitle("Menu Gestor - Produtos");
        stage.show();
    }
    public void purchaseButtonOnAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/IPVC/views/Gestor/Purchase/purchaseGestor.fxml"));
        Scene regCena = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(regCena);
        stage.setTitle("Menu Gestor - Compras");
        stage.show();
    }
    public void salesButtonOnAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/IPVC/views/Gestor/Sales/salesGestor.fxml"));
        Scene regCena = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(regCena);
        stage.setTitle("Menu Gestor - Recibos");
        stage.show();
    }
    public void productionButtonOnAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/IPVC/views/Gestor/Production/productionGestor.fxml"));
        Scene regCena = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(regCena);
        stage.setTitle("Menu Gestor - Produção");
        stage.show();
    }
    public void packagingButtonOnAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/IPVC/views/Gestor/Packaging/packagingGestor.fxml"));
        Scene regCena = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(regCena);
        stage.setTitle("Menu Gestor - Embalamento");
        stage.show();
    }
    public void employeesButtonOnAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/IPVC/views/Gestor/Employees/employeeGestor.fxml"));
        Scene regCena = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(regCena);
        stage.setTitle("Menu Gestor - Funcionários");
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
            Parent root = FXMLLoader.load(getClass().getResource("/IPVC/views/Login.fxml"));
            Scene regCena = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(regCena);
            stage.setTitle("Login");
            stage.show();
        }
    }
}