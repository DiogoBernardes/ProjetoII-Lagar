package IPVC.Admin.Sales;

import IPVC.Admin.Purchase.editPurchaseController;
import IPVC.BLL.FaturaBLL;
import IPVC.BLL.LinhaFaturaBLL;
import IPVC.BLL.LinhaReciboBLL;
import IPVC.BLL.ReciboBLL;
import IPVC.DAL.Fatura;
import IPVC.DAL.LinhaFatura;
import IPVC.DAL.LinhaRecibo;
import IPVC.DAL.Recibo;
import javafx.application.Platform;
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
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class salesController {
    @FXML
    private TableView<LinhaRecibo> dataView;
    @FXML
    private TableColumn<LinhaRecibo, String> reciboColumn;
    @FXML
    private TableColumn<LinhaRecibo, String> produtoColumn;
    @FXML
    private TableColumn<LinhaRecibo, String> quantidadeColumn;
    @FXML
    private TableColumn<LinhaRecibo, String> valorColumn;
    @FXML
    private TableColumn<LinhaRecibo, String> valorFinalColumn;
    @FXML
    private TableColumn<LinhaRecibo, String> clienteColumn;
    @FXML
    private TableColumn<LinhaRecibo, String> pagamentoColumn;
    @FXML
    private TableColumn<LinhaRecibo, String> dataColumn;
    @FXML
    private TextField searchTF;
    @FXML
    private void initialize() {
        List<LinhaRecibo> linha_recibos = LinhaReciboBLL.index();
        //Ordenar a Lista pelo IdRecibo
        Collections.sort(linha_recibos, Comparator.comparingInt(recibo -> recibo.getRecibo().getId_Recibo()));
        ObservableList<LinhaRecibo> data = FXCollections.observableArrayList(linha_recibos);

        dataView.setItems(data);
        reciboColumn.setCellValueFactory(d -> new SimpleStringProperty(String.valueOf(d.getValue().getRecibo().getId_Recibo())));
        produtoColumn.setCellValueFactory(d -> new SimpleStringProperty(String.valueOf(d.getValue().getProduto().getNome())));
        quantidadeColumn.setCellValueFactory(d -> new SimpleStringProperty(String.valueOf(d.getValue().getQuantidade())));
        valorColumn.setCellValueFactory(d -> new SimpleStringProperty(String.valueOf(d.getValue().getValor())));
        valorFinalColumn.setCellValueFactory(d -> new SimpleStringProperty(String.valueOf(d.getValue().getRecibo().getValor_Final())));
        clienteColumn.setCellValueFactory(d -> new SimpleStringProperty(String.valueOf(d.getValue().getRecibo().getEntidade().getNome())));
        pagamentoColumn.setCellValueFactory(d -> new SimpleStringProperty(String.valueOf(d.getValue().getRecibo().getTipoPagamento().getDescricao())));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        dataColumn.setCellValueFactory(d -> new SimpleStringProperty(String.valueOf(sdf.format(d.getValue().getRecibo().getData()))));

        // Cria um FilteredList com a lista de clientes
        FilteredList<LinhaRecibo> filteredData = new FilteredList<>(data, p -> true);

        // Adiciona um listener ao TextField de busca para filtrar a tabela quando o texto mudar
        searchTF.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(recibo -> {
                // Se o texto de busca estiver vazio, exibe todos os clientes
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase().trim();

                if (String.valueOf(recibo.getRecibo().getId_Recibo()).contains(lowerCaseFilter)) {
                    return true;
                } else if (String.valueOf(recibo.getProduto().getNome()).contains(lowerCaseFilter)) {
                    return true;
                } else if (recibo.getRecibo().getEntidade().getNome().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (String.valueOf(recibo.getRecibo().getData()).contains(lowerCaseFilter)) {
                    return true;
                } else if(String.valueOf(recibo.getRecibo().getValor_Final()).contains(lowerCaseFilter)){
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
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/IPVC/views/Admin/Sales/addSales.fxml"));
        Parent parent = fxmlLoader.load();
        Scene scene = new Scene(parent);
        Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.initOwner(((Node) event.getSource()).getScene().getWindow());
        dialogStage.setTitle("Adicionar Recibo");
        dialogStage.setScene(scene);
        dialogStage.showAndWait();

        List<LinhaRecibo> linha_recibos = LinhaReciboBLL.index();
        updateDataView(linha_recibos);
    }
    public void removeButtonOnAction(ActionEvent event) throws IOException {
        LinhaRecibo selectedRecibo = dataView.getSelectionModel().getSelectedItem();
        if (selectedRecibo != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Remover Recibo");
            alert.setHeaderText("Tem a certeza que deseja remover o recibo '" + selectedRecibo.getRecibo().getId_Recibo() + "'?");

            ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
            ButtonType cancelButton = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(okButton, cancelButton);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == okButton) {
                LinhaReciboBLL.removeByRecibo(selectedRecibo.getRecibo().getId_Recibo());
                ReciboBLL.remove(selectedRecibo.getRecibo().getId_Recibo());
                List<LinhaRecibo> linha_recibos = LinhaReciboBLL.index();
                updateDataView(linha_recibos);

                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setTitle("Remoção bem-sucedida");
                successAlert.setHeaderText(null);
                successAlert.setContentText("A fatura foi removida com sucesso!");
                successAlert.showAndWait();
            } else {
                alert.close();
            }
        }else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Remover Recibo");
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
        LinhaRecibo selectedLinhaRecibo = dataView.getSelectionModel().getSelectedItem();
        if (selectedLinhaRecibo != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/IPVC/views/Admin/Sales/editSales.fxml"));
            Parent parent = loader.load();
            editSalesController controller = loader.getController();
            Recibo selectedRecibo = selectedLinhaRecibo.getRecibo();
            controller.setSales(selectedLinhaRecibo,selectedRecibo);
            Scene scene = new Scene(parent);
            Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.initOwner(((Node) event.getSource()).getScene().getWindow());
            dialogStage.setTitle("Editar Recibo");
            dialogStage.setScene(scene);
            dialogStage.showAndWait();
            dataView.refresh();

        }else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Editar Recibo");
            alert.setHeaderText("Para remover um recibo é necessário seleciona-lo na tabela!");

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
    public void clientButtonOnAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/IPVC/views/Admin/Client/clientAdmin.fxml"));
        Scene regCena = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(regCena);
        stage.setTitle("Menu Admin - Faturas");
        stage.show();
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
    public void employeesButtonOnAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/IPVC/views/Admin/Employees/employeeAdmin.fxml"));
        Scene regCena = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(regCena);
        stage.setTitle("Menu Admin - Funcionários");
        stage.show();
    }
    private void updateDataView(List<LinhaRecibo> linha_recibos) {
        Collections.sort(linha_recibos, Comparator.comparingInt(recibo -> recibo.getRecibo().getId_Recibo()));
        ObservableList<LinhaRecibo> data = FXCollections.observableArrayList(linha_recibos);
        dataView.setItems(data);
        dataView.refresh();
    }

}