package IPVC.Admin.Production;

import IPVC.BLL.*;
import IPVC.DAL.*;
import IPVC.views.Session;
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
import java.util.Locale;

public class addProductionController {

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
    private TextField acidezTF;

    @FXML
    private Button closeButton;
    @FXML
    private Label Details;

    @FXML
    private void initialize() throws IOException{
        List<Produto> prodMP = ProdutoBLL.getTypeProduct(1);
        List<Produto> prodFinal = ProdutoBLL.getTypeProduct(2);

        ObservableList<String> produtoMP = FXCollections.observableArrayList();
        ObservableList<String> produtoF = FXCollections.observableArrayList();

        for (Produto p : prodMP) {
            produtoMP.add(p.getNome());
        }
        for (Produto p : prodFinal) {
            produtoF.add(p.getNome());
        }

        produtoCB.setItems(produtoMP);
        quantidadeTF.setText("0");
        produtoFCB.setItems(produtoF);
        qtdProdTF.setText("0");
        acidezTF.setText("0");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        Date dataAtual = new Date();
        String dataFormatada = sdf.format(dataAtual);
        dataTF.setText(dataFormatada);

    }



    public void addProductionButtonOnAction(ActionEvent event) throws IOException, ParseException {
        if (!produtoCB.getValue().isEmpty() || !produtoFCB.getValue().isEmpty() || !quantidadeTF.getText().isEmpty() ||
                !qtdProdTF.getText().isEmpty() || !acidezTF.getText().isEmpty()){

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            Date data = sdf.parse(dataTF.getText());
            String produtoMPNome = produtoCB.getSelectionModel().getSelectedItem();
            String produtoFinalNome = produtoFCB.getSelectionModel().getSelectedItem();
            Produto produtomp = ProdutoBLL.getName(produtoMPNome);
            Produto produtoFinal = ProdutoBLL.getName(produtoFinalNome);


            Producao newProducao = new Producao();
            ProdutoMP newProdutoMP = new ProdutoMP();
            newProducao.setProduto(produtoFinal);
            newProducao.setQtd_Produzida(Integer.parseInt(qtdProdTF.getText()));
            newProducao.setData(data);
            newProducao.setAcidez(Double.parseDouble(acidezTF.getText()));

            newProdutoMP.setProduto(produtomp);
            newProdutoMP.setQuantidade(Integer.parseInt(quantidadeTF.getText()));
            newProdutoMP.setProducao(newProducao);


            ProducaoBLL.create(newProducao);
            ProdutoMPBLL.create(newProdutoMP);

            Details.getStyleClass().add("valid-details");
            Details.setText("Produção inserida com Sucesso!");
            produtoCB.setValue("");
            quantidadeTF.setText("");
            qtdProdTF.setText("");
            produtoFCB.setValue("");
            acidezTF.setText("");
            dataTF.setText("");

            PauseTransition pause = new PauseTransition(Duration.seconds(1.5));
            pause.setOnFinished(e -> {
                Details.setText("");
                Details.getStyleClass().removeAll("invalid-details-error");
            });
            pause.play();
        }else {
            Details.getStyleClass().add("invalid-details-error");
            Details.setText("Os dados da produção são necessários!");
            produtoCB.getStyleClass().add("TF-EmptyLogin");
            produtoFCB.getStyleClass().add("TF-EmptyLogin");
            qtdProdTF.getStyleClass().add("TF-EmptyLogin");
            quantidadeTF.getStyleClass().add("TF-EmptyLogin");
            acidezTF.getStyleClass().add("TF-EmptyLogin");
            dataTF.getStyleClass().add("TF-EmptyLogin");
            PauseTransition pause = new PauseTransition(Duration.seconds(1.5));
            pause.setOnFinished(e -> {
                Details.setText("");
                Details.getStyleClass().removeAll("invalid-details-error");
                produtoCB.getStyleClass().removeAll("TF-EmptyLogin");
                produtoFCB.getStyleClass().removeAll("TF-EmptyLogin");
                qtdProdTF.getStyleClass().removeAll("TF-EmptyLogin");
                quantidadeTF.getStyleClass().removeAll("TF-EmptyLogin");
                acidezTF.getStyleClass().removeAll("TF-EmptyLogin");
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
