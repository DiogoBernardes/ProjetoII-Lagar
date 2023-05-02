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

public class editProductionController {
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

    private ProdutoMP produto;
    private Producao producao;

    public void setProduction(ProdutoMP ProdutoMP, Producao producoes) {
        this.produto = ProdutoMP;
        this.producao = producoes;
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
        String produtoSelecionado = produto.getProduto().getNome();
        if (produtoMP.contains(produtoSelecionado)) {
            produtoCB.setValue(produtoSelecionado);
        }
        String produtoFinalSelecionado = producao.getProduto().getNome();
        if (produtoF.contains(produtoFinalSelecionado)) {
            produtoFCB.setValue(produtoFinalSelecionado);
        }

        quantidadeTF.setText(String.valueOf(produto.getQuantidade()));;
        qtdProdTF.setText(String.valueOf(Math.round(producao.getQtd_Produzida())));
        acidezTF.setText(String.valueOf(producao.getAcidez()));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        dataTF.setText(sdf.format(producao.getData()));
    }


    @FXML
    private void updateProduction(ActionEvent event) throws IOException, ParseException {
       if (producao != null && produto != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            Date data = sdf.parse(dataTF.getText());


            String produtoMPNome = produtoCB.getSelectionModel().getSelectedItem();
            String produtoFinalNome = produtoFCB.getSelectionModel().getSelectedItem();
            Produto produtomp = ProdutoBLL.getName(produtoMPNome);
            Produto produtoFinal = ProdutoBLL.getName(produtoFinalNome);

            producao.setProduto(produtoFinal);
            producao.setQtd_Produzida(Integer.parseInt(qtdProdTF.getText()));
            producao.setAcidez(Double.parseDouble(acidezTF.getText()));
            producao.setData(data);

            produto.setProducao(producao);
            produto.setProduto(produtomp);
            produto.setQuantidade(Integer.parseInt(quantidadeTF.getText()));

            ProducaoBLL.update(producao);
            ProdutoMPBLL.update(produto);

            Details.getStyleClass().add("valid-details");
            Details.setText("Produção editada com Sucesso!");
            PauseTransition pause = new PauseTransition(Duration.seconds(1.5));
            pause.setOnFinished(e -> {
                Details.setText("");
                Details.getStyleClass().removeAll("invalid-details-error");
            });
            pause.play();
       }else{
           Details.getStyleClass().add("valid-details");
           Details.setText("Erro na edição da fatura!");
           PauseTransition pause = new PauseTransition(Duration.seconds(1.5));
           pause.setOnFinished(e -> {
               Details.setText("");
               Details.getStyleClass().removeAll("invalid-details-error");
           });
           pause.play();
       }
    }

    public void closeButtonOnAction(ActionEvent event) throws IOException {
        Stage dialogStage = (Stage) closeButton.getScene().getWindow();
        dialogStage.close();
    }


}
