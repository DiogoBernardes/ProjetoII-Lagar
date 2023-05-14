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

public class editPackagingController {
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

    private ProdutoEMB produto;
    private Embalamento embalamento;

    public void setPackaging(ProdutoEMB produtoEMB, Embalamento embalamentos) {
        this.produto = produtoEMB;
        this.embalamento = embalamentos;
        List<Produto> prodEMB = ProdutoBLL.getTypeProduct(3);
        List<Produto> prodEMB2 = ProdutoBLL.getTypeProduct(4);
        List<Produto> prodFinal = ProdutoBLL.getTypeProduct(5);

        ObservableList<String> produtoEMB1 = FXCollections.observableArrayList();
        ObservableList<String> produtoEMB2 = FXCollections.observableArrayList();
        ObservableList<String> produtoF = FXCollections.observableArrayList();

        for (Produto p : prodEMB) {
            produtoEMB1.add(p.getNome());
        }
        for (Produto p : prodEMB2) {
            produtoEMB2.add(p.getNome());
        }
        for (Produto p : prodFinal) {
            produtoF.add(p.getNome());
        }

        String produtoSelecionado = produto.getProduto().getNome();
        if (produtoEMB1.contains(produtoSelecionado)) {
            produtoCB.setValue(produtoSelecionado);
        }
        String produtoEMB2Selecionado = produto.getProduto().getNome();
        if (produtoEMB2.contains(produtoEMB2Selecionado)) {
            produtoCB.setValue(produtoEMB2Selecionado);
        }
        String produtoFinalSelecionado = embalamentos.getProduto().getNome();
        if (produtoF.contains(produtoFinalSelecionado)) {
            produtoFCB.setValue(produtoFinalSelecionado);
        }
        produtoCB.setItems(produtoEMB1);
        produtoCB.getItems().addAll(produtoEMB2);
        produtoFCB.setItems(produtoF);
        quantidadeTF.setText(String.valueOf(produtoEMB.getQuantidade()));;
        qtdProdTF.setText(String.valueOf(Math.round(embalamentos.getQtd_Embalada())));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        dataTF.setText(sdf.format(embalamentos.getData()));
    }
    @FXML
    private void updateProduction(ActionEvent event) throws IOException, ParseException {

        String prodNome= produtoCB.getSelectionModel().getSelectedItem();
        Produto prod = ProdutoBLL.getName(prodNome);
        int prodQuantidade = prod.getQuantidade();
        int qtdInserida = Integer.parseInt(quantidadeTF.getText());


        if (embalamento == null || produto == null) {

           Details.getStyleClass().add("valid-details");
           Details.setText("Erro na edição do embalamento!");
           PauseTransition pause = new PauseTransition(Duration.seconds(1.5));
           pause.setOnFinished(e -> {
               Details.setText("");
               Details.getStyleClass().removeAll("invalid-details-error");
           });
           pause.play();


       }

        if(qtdInserida <= prodQuantidade) {

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            Date data = sdf.parse(dataTF.getText());

            String produtoMPNome = produtoCB.getSelectionModel().getSelectedItem();
            String produtoFinalNome = produtoFCB.getSelectionModel().getSelectedItem();
            Produto produtomp = ProdutoBLL.getName(produtoMPNome);
            Produto produtoFinal = ProdutoBLL.getName(produtoFinalNome);

            embalamento.setProduto(produtoFinal);
            embalamento.setQtd_Embalada(Integer.parseInt(qtdProdTF.getText()));
            embalamento.setData(data);


            produto.setEmbalamento(embalamento);
            produto.setProduto(produtomp);
            produto.setQuantidade(Integer.parseInt(quantidadeTF.getText()));

            EmbalamentoBLL.update(embalamento);
            ProdutoEMBBLL.update(produto);

            Details.getStyleClass().add("valid-details");
            Details.setText("Embalamento editado com Sucesso!");
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
