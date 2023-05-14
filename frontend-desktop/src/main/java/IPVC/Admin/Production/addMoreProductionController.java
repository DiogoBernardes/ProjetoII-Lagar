package IPVC.Admin.Production;

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

public class addMoreProductionController {
    @FXML
    private ComboBox<Producao> producaoCB;
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
    private TextField acidezTF;
    @FXML
    private Button closeButton;
    @FXML
    private Label Details;


    @FXML
    private void initialize() throws IOException{
        List<Produto> prod = ProdutoBLL.getTypeProduct(1);
        List<Produto> prodFinal = ProdutoBLL.getTypeProduct(2);
        List<Producao> producao = ProducaoBLL.index();

        ObservableList<String> prodMP = FXCollections.observableArrayList();
        ObservableList<String> produtoF = FXCollections.observableArrayList();
        ObservableList<Producao> producaoList = FXCollections.observableArrayList();

        Collections.sort(producao, Comparator.comparingInt(Producao::getId_Producao));

        for (Produto p : prod) {
            prodMP.add(p.getNome());
        }
        for (Produto p : prodFinal) {
            produtoF.add(p.getNome());
        }
        for(Producao p : producao){
            producaoList.add(p);
        }

        produtoCB.setItems(prodMP);
        quantidadeTF.setText("0");
        producaoCB.setItems(producaoList);
        producaoCB.setOnAction(event -> {
            Producao selectedProducao = producaoCB.getSelectionModel().getSelectedItem();
            if (selectedProducao != null) {
                produtoFTF.setText(selectedProducao.getProduto().getNome());
                qtdProdTF.setText(String.valueOf(selectedProducao.getQtd_Produzida()));
                acidezTF.setText(String.valueOf(selectedProducao.getAcidez()));
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                dataTF.setText(sdf.format(selectedProducao.getData()));
            }
        });
    }
    public void addProductionButtonOnAction(ActionEvent event) throws IOException, ParseException {

        String prodNome= produtoCB.getSelectionModel().getSelectedItem();
        Produto prod = ProdutoBLL.getName(prodNome);
        int prodQuantidade = prod.getQuantidade();
        int qtdInserida = Integer.parseInt(quantidadeTF.getText());


        if (produtoCB.getValue().isEmpty() || quantidadeTF.getText().isEmpty() || (producaoCB.getValue() == null)){

            Details.getStyleClass().add("invalid-details-error");
            Details.setText("Os dados de Embalamento são necessários!");
            produtoCB.getStyleClass().add("TF-EmptyLogin");
            produtoFTF.getStyleClass().add("TF-EmptyLogin");
            qtdProdTF.getStyleClass().add("TF-EmptyLogin");
            quantidadeTF.getStyleClass().add("TF-EmptyLogin");
            dataTF.getStyleClass().add("TF-EmptyLogin");
            producaoCB.getStyleClass().add("TF-EmptyLogin");
            PauseTransition pause = new PauseTransition(Duration.seconds(1.5));
            pause.setOnFinished(e -> {
                Details.setText("");
                Details.getStyleClass().removeAll("invalid-details-error");
                produtoCB.getStyleClass().removeAll("TF-EmptyLogin");
                produtoFTF.getStyleClass().removeAll("TF-EmptyLogin");
                qtdProdTF.getStyleClass().removeAll("TF-EmptyLogin");
                quantidadeTF.getStyleClass().removeAll("TF-EmptyLogin");
                producaoCB.getStyleClass().removeAll("TF-EmptyLogin");
                dataTF.getStyleClass().removeAll("TF-EmptyLogin");
            });
            pause.play();


        }

        if(qtdInserida <= prodQuantidade) {

            String produtoMPNome = produtoCB.getSelectionModel().getSelectedItem();
            Produto produtoMP = ProdutoBLL.getName(produtoMPNome);
            Producao producao = producaoCB.getSelectionModel().getSelectedItem();

            ProdutoMP newProdutoMP = new ProdutoMP();

            newProdutoMP.setProduto(produtoMP);
            newProdutoMP.setQuantidade(Integer.parseInt(quantidadeTF.getText()));
            newProdutoMP.setProducao(producao);

            ProdutoMPBLL.create(newProdutoMP);

            Details.getStyleClass().add("valid-details");
            Details.setText("Produto adicionado ao embalamento com Sucesso!");
            produtoCB.setValue("");
            quantidadeTF.setText("");
            qtdProdTF.setText("");
            produtoFTF.setText("");
            dataTF.setText("");
            producaoCB.setValue(null);

            PauseTransition pause = new PauseTransition(Duration.seconds(1.5));
            pause.setOnFinished(e -> {
                Details.setText("");
                Details.getStyleClass().removeAll("invalid-details-error");
            });
            pause.play();
        }else{
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
