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
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class addMorePackagingController {

    @FXML
    private ComboBox<String> produtoCB;
    @FXML
    private TextField quantidadeTF;
    @FXML
    private TextField produtoFTF;
    @FXML
    private TextField qtdProdTF;
    @FXML
    private TextField dataTF;
    @FXML
    private ComboBox<Embalamento> embalamentoCB;

    @FXML
    private Button closeButton;
    @FXML
    private Label Details;

    @FXML
    private void initialize() throws IOException{
        List<Produto> prodEMB = ProdutoBLL.getTypeProduct(3);
        List<Produto> prodEMB2 = ProdutoBLL.getTypeProduct(4);
        List<Produto> prodFinal = ProdutoBLL.getTypeProduct(5);
        List<Embalamento> embalamentos = EmbalamentoBLL.index();

        ObservableList<String> produtoEMB = FXCollections.observableArrayList();
        ObservableList<String> produtoEMB2 = FXCollections.observableArrayList();
        ObservableList<String> produtoF = FXCollections.observableArrayList();
        ObservableList<Embalamento> embalamentosList = FXCollections.observableArrayList();

        Collections.sort(embalamentos, Comparator.comparingInt(Embalamento::getId_Embalamento));


        for (Produto p : prodEMB) {
            produtoEMB.add(p.getNome());
        }
        for (Produto p : prodEMB2) {
            produtoEMB2.add(p.getNome());
        }
        for (Produto p : prodFinal) {
            produtoF.add(p.getNome());
        }
        for(Embalamento e : embalamentos){
            embalamentosList.add(e);
        }

        produtoCB.setItems(produtoEMB);
        produtoCB.getItems().addAll(produtoEMB2);
        quantidadeTF.setText("0");
        embalamentoCB.setOnAction(event -> {
            Embalamento selectedEmbalamento = embalamentoCB.getSelectionModel().getSelectedItem();
            if (selectedEmbalamento != null) {
                produtoFTF.setText(selectedEmbalamento.getProduto().getNome());
                qtdProdTF.setText(String.valueOf(selectedEmbalamento.getProduto().getQuantidade()));
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                dataTF.setText(sdf.format(selectedEmbalamento.getData()));

            }
        });
        embalamentoCB.setItems(embalamentosList);
    }

    public void addProductionButtonOnAction(ActionEvent event) throws IOException, ParseException {

        String prodNome= produtoCB.getSelectionModel().getSelectedItem();
        Produto prod = ProdutoBLL.getName(prodNome);
        int prodQuantidade = prod.getQuantidade();
        int qtdInserida = Integer.parseInt(quantidadeTF.getText());


        if (produtoCB.getValue().isEmpty() ||  quantidadeTF.getText().isEmpty() || (embalamentoCB.getValue() == null)){
            Details.getStyleClass().add("invalid-details-error");
            Details.setText("Os dados de Embalamento são necessários!");
            produtoCB.getStyleClass().add("TF-EmptyLogin");
            produtoFTF.getStyleClass().add("TF-EmptyLogin");
            qtdProdTF.getStyleClass().add("TF-EmptyLogin");
            quantidadeTF.getStyleClass().add("TF-EmptyLogin");
            dataTF.getStyleClass().add("TF-EmptyLogin");
            embalamentoCB.getStyleClass().add("TF-EmptyLogin");
            PauseTransition pause = new PauseTransition(Duration.seconds(1.5));
            pause.setOnFinished(e -> {
                Details.setText("");
                Details.getStyleClass().removeAll("invalid-details-error");
                produtoCB.getStyleClass().removeAll("TF-EmptyLogin");
                produtoFTF.getStyleClass().removeAll("TF-EmptyLogin");
                qtdProdTF.getStyleClass().removeAll("TF-EmptyLogin");
                quantidadeTF.getStyleClass().removeAll("TF-EmptyLogin");
                embalamentoCB.getStyleClass().removeAll("TF-EmptyLogin");
                dataTF.getStyleClass().removeAll("TF-EmptyLogin");
            });
            pause.play();


        }

        if(qtdInserida <= prodQuantidade) {String produtoEMBNome = produtoCB.getSelectionModel().getSelectedItem();
            Produto produtoEMB = ProdutoBLL.getName(produtoEMBNome);
            Embalamento embalamento = embalamentoCB.getSelectionModel().getSelectedItem();

            ProdutoEMB newProdutoEMB = new ProdutoEMB();

            newProdutoEMB.setProduto(produtoEMB);
            newProdutoEMB.setQuantidade(Integer.parseInt(quantidadeTF.getText()));
            newProdutoEMB.setEmbalamento(embalamento);

            ProdutoEMBBLL.create(newProdutoEMB);

            Details.getStyleClass().add("valid-details");
            Details.setText("Produto adicionado ao embalamento com Sucesso!");
            produtoCB.setValue("");
            quantidadeTF.setText("");
            qtdProdTF.setText("");
            produtoFTF.setText("");
            dataTF.setText("");
            embalamentoCB.setValue(null);

            PauseTransition pause = new PauseTransition(Duration.seconds(1.5));
            pause.setOnFinished(e -> {
                Details.setText("");
                Details.getStyleClass().removeAll("invalid-details-error");
            });
            pause.play();
        } else{
            Details.getStyleClass().add("invalid-details-error");
            Details.setText("Não existe stock suficiente desse produto!");
            quantidadeTF.getStyleClass().add("TF-EmptyLogin");
            PauseTransition pause = new PauseTransition(Duration.seconds(1.5));
            pause.setOnFinished(e -> {
                Details.setText("");
                quantidadeTF.getStyleClass().removeAll("TF-EmptyLogin");
            });
            pause.play();
        }
    }

    public void closeButtonOnAction(ActionEvent event) throws IOException {
        Stage dialogStage = (Stage) closeButton.getScene().getWindow();
        dialogStage.close();
    }

}
