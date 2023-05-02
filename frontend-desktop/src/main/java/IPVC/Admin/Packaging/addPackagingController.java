package IPVC.Admin.Packaging;

import IPVC.BLL.*;
import IPVC.DAL.*;
import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class addPackagingController {

    @FXML
    private ComboBox<String> produtoCB;
    @FXML
    private TextField quantidadeTF;
    @FXML
    private ComboBox<String> produtoFCB;
    @FXML
    private TextField qtdProdTF;
    @FXML
    private TextField dataTF;

    @FXML
    private Button closeButton;
    @FXML
    private Label Details;

    @FXML
    private void initialize() throws IOException{
        List<Produto> prodEMB = ProdutoBLL.getTypeProduct(3);
        List<Produto> prodEMB2 = ProdutoBLL.getTypeProduct(4);
        List<Produto> prodFinal = ProdutoBLL.getTypeProduct(5);

        ObservableList<String> produtoEMB = FXCollections.observableArrayList();
        ObservableList<String> produtoEMB2 = FXCollections.observableArrayList();
        ObservableList<String> produtoF = FXCollections.observableArrayList();

        for (Produto p : prodEMB) {
            produtoEMB.add(p.getNome());
        }
        for (Produto p : prodEMB2) {
            produtoEMB2.add(p.getNome());
        }
        for (Produto p : prodFinal) {
            produtoF.add(p.getNome());
        }

        produtoCB.setItems(produtoEMB);
        produtoCB.getItems().addAll(produtoEMB2);
        quantidadeTF.setText("0");
        produtoFCB.setItems(produtoF);
        qtdProdTF.setText("0");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        Date dataAtual = new Date();
        String dataFormatada = sdf.format(dataAtual);
        dataTF.setText(dataFormatada);

    }

    public void addPackagingButtonOnAction(ActionEvent event) throws IOException, ParseException {
        if (!produtoCB.getValue().isEmpty() || !produtoFCB.getValue().isEmpty() || !quantidadeTF.getText().isEmpty() || !qtdProdTF.getText().isEmpty()){

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            Date data = sdf.parse(dataTF.getText());
            String produtoEMBNome = produtoCB.getSelectionModel().getSelectedItem();
            String produtoFinalNome = produtoFCB.getSelectionModel().getSelectedItem();
            Produto produtoEMB = ProdutoBLL.getName(produtoEMBNome);
            Produto produtoFinal = ProdutoBLL.getName(produtoFinalNome);


            Embalamento newEmbalamento = new Embalamento();
            ProdutoEMB newProdutoEMB = new ProdutoEMB();

            newEmbalamento.setProduto(produtoFinal);
            newEmbalamento.setQtd_Embalada(Integer.parseInt(qtdProdTF.getText()));
            newEmbalamento.setData(data);

            newProdutoEMB.setProduto(produtoEMB);
            newProdutoEMB.setQuantidade(Integer.parseInt(quantidadeTF.getText()));
            newProdutoEMB.setEmbalamento(newEmbalamento);


            EmbalamentoBLL.create(newEmbalamento);
            ProdutoEMBBLL.create(newProdutoEMB);

            Details.getStyleClass().add("valid-details");
            Details.setText("Embalamento inserida com Sucesso!");
            produtoCB.setValue("");
            quantidadeTF.setText("");
            qtdProdTF.setText("");
            produtoFCB.setValue("");
            dataTF.setText("");

            PauseTransition pause = new PauseTransition(Duration.seconds(1.5));
            pause.setOnFinished(e -> {
                Details.setText("");
                Details.getStyleClass().removeAll("invalid-details-error");
            });
            pause.play();
        }else {
            Details.getStyleClass().add("invalid-details-error");
            Details.setText("Os dados do embalamento são necessários!");
            produtoCB.getStyleClass().add("TF-EmptyLogin");
            produtoFCB.getStyleClass().add("TF-EmptyLogin");
            qtdProdTF.getStyleClass().add("TF-EmptyLogin");
            quantidadeTF.getStyleClass().add("TF-EmptyLogin");
            dataTF.getStyleClass().add("TF-EmptyLogin");
            PauseTransition pause = new PauseTransition(Duration.seconds(1.5));
            pause.setOnFinished(e -> {
                Details.setText("");
                Details.getStyleClass().removeAll("invalid-details-error");
                produtoCB.getStyleClass().removeAll("TF-EmptyLogin");
                produtoFCB.getStyleClass().removeAll("TF-EmptyLogin");
                qtdProdTF.getStyleClass().removeAll("TF-EmptyLogin");
                quantidadeTF.getStyleClass().removeAll("TF-EmptyLogin");
                dataTF.getStyleClass().removeAll("TF-EmptyLogin");
            });
            pause.play();
        }
    }

    public void closeButtonOnAction(ActionEvent event) throws IOException {
        Stage dialogStage = (Stage) closeButton.getScene().getWindow();
        dialogStage.close();
    }

}
