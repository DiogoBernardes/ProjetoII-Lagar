package IPVC.Admin.Purchase;

import IPVC.BLL.*;
import IPVC.DAL.*;
import IPVC.views.LoginController;
import IPVC.views.Session;
import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

public class addPurchaseController {

    @FXML
    private ComboBox<String> produtoCB;
    @FXML
    private TextField quantidadeTF;
    @FXML
    private ComboBox<String> fornecedorCB;
    @FXML
    private ComboBox<String> pagamentoCB;
    @FXML
    private ComboBox<String> tipoProdutoCB;
    @FXML
    private TextField dataTF;
    @FXML
    private TextField utilizadorTF;
    @FXML
    private TextField valorTF;
    @FXML
    private TextField valorFinalTF;
    @FXML
    private TextField valorUnitarioTF;
    @FXML
    private Button closeButton;
    @FXML
    private Label Details;
    private Utilizador currentUser = Session.getInstance().getCurrentUser();

    @FXML
    private void initialize() throws IOException{

        List<Entidade> entidades = EntidadeBLL.getEntities(1);
        List<TipoPagamento> pagamentos = TipoPagamentoBLL.index();
        List<TipoProduto> tiposProduto = TipoProdutoBLL.index();


        ObservableList<String> fornecedor = FXCollections.observableArrayList();
        ObservableList<String> pagamento = FXCollections.observableArrayList();
        ObservableList<String> tiposProdutoDescricoes = FXCollections.observableArrayList();


        for (Entidade e : entidades) {
            fornecedor.add(e.getNome());
        }
        for (TipoPagamento tp : pagamentos) {
            pagamento.add(tp.getDescricao());
        }
        for (TipoProduto tipo : tiposProduto) {
            tiposProdutoDescricoes.add(tipo.getDescricao());
        }
        fornecedorCB.setItems(fornecedor);
        pagamentoCB.setItems(pagamento);
        tipoProdutoCB.setItems(tiposProdutoDescricoes);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        Date dataAtual = new Date();
        String dataFormatada = sdf.format(dataAtual);
        dataTF.setText(dataFormatada);
        utilizadorTF.setText(currentUser.getNome());
        quantidadeTF.setText("0");
        valorUnitarioTF.setText("0  ");

        // Adiciona listeners para atualizar o valor final
        quantidadeTF.textProperty().addListener((observable, oldValue, newValue) -> {
            atualizarValor();
        });

        valorTF.textProperty().addListener((observable, oldValue, newValue) -> {
            atualizarValorFinal();
        });
        valorUnitarioTF.textProperty().addListener((observable, oldValue, newValue) -> {
            atualizarValor();
        });

        tipoProdutoCB.valueProperty().addListener((observable, oldValue, newValue) -> {
            atualizarProduto();
        });
    }
    private void atualizarProduto(){
        String tipoProdutoDescricao = tipoProdutoCB.getSelectionModel().getSelectedItem();
        TipoProduto tipoProduto = TipoProdutoBLL.getByDescription(tipoProdutoDescricao);
        List<Produto> produtos = ProdutoBLL.getTypeProduct(tipoProduto.getId_TipoProduto());
        ObservableList<String> produto = FXCollections.observableArrayList();
        for (Produto p : produtos) {
            produto.add(p.getNome());
        }
        produtoCB.setItems(produto);
    }

    private void atualizarValor(){

        Double valor = Integer.parseInt(valorUnitarioTF.getText())* Double.parseDouble(quantidadeTF.getText());

        valorTF.setText(String.format(Locale.US, "%.2f", valor));
    }
    private void atualizarValorFinal() {
        double valor = Double.parseDouble(valorTF.getText());
        double valorFinal = valor * 1.23;
        valorFinalTF.setText(String.format(Locale.US, "%.2f", valorFinal));
    }


    //Problemas com a linhaFatura,diz que o Id_Produto não existe..
    public void addPurchaseButtonOnAction(ActionEvent event) throws IOException, ParseException {
        if (!produtoCB.getValue().isEmpty() || !fornecedorCB.getValue().isEmpty() || !quantidadeTF.getText().isEmpty() || !pagamentoCB.getValue().isEmpty()) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            Date data = sdf.parse(dataTF.getText());
            String fornecedorNome = fornecedorCB.getSelectionModel().getSelectedItem();
            String pagamentoDescricao = pagamentoCB.getSelectionModel().getSelectedItem();
            String produtoNome = produtoCB.getSelectionModel().getSelectedItem();
            Entidade fornecedor = EntidadeBLL.getEntityByName(fornecedorNome);
            TipoPagamento pagamento = TipoPagamentoBLL.getPaymentByDescription(pagamentoDescricao);
            Produto produto = ProdutoBLL.getName(produtoNome);

            Fatura newFatura = new Fatura();
            LinhaFatura newLinhaFatura = new LinhaFatura();
            newFatura.setEntidade(fornecedor);
            newFatura.setUtilizador(currentUser);
            newFatura.setData(data);
            newFatura.setValor(Double.parseDouble(valorTF.getText()));
            newFatura.setIva(1.23);
            newFatura.setValor_Total(Double.parseDouble(valorTF.getText()) * 1.23);
            newFatura.setTipoPagamento(pagamento);

            newLinhaFatura.setProduto(produto);
            newLinhaFatura.setQuantidade(Integer.parseInt(quantidadeTF.getText()));
            newLinhaFatura.setValor(Double.parseDouble(valorTF.getText()));
            newLinhaFatura.setFatura(newFatura);

            FaturaBLL.create(newFatura);
            LinhaFaturaBLL.create(newLinhaFatura);

            Details.getStyleClass().add("valid-details");
            Details.setText("Fatura inserida com Sucesso!");
            produtoCB.setValue("");
            valorTF.setText("");
            valorFinalTF.setText("");
            quantidadeTF.setText("");
            dataTF.setText("");
            fornecedorCB.setValue("");
            utilizadorTF.setText("");
            pagamentoCB.setValue("");

            PauseTransition pause = new PauseTransition(Duration.seconds(1.5));
            pause.setOnFinished(e -> {
                Details.setText("");
                Details.getStyleClass().removeAll("invalid-details-error");
            });
            pause.play();
        }else {
            Details.getStyleClass().add("invalid-details-error");
            Details.setText("Os dados da Fatura são necessários!");
            produtoCB.getStyleClass().add("TF-EmptyLogin");
            valorTF.getStyleClass().add("TF-EmptyLogin");
            quantidadeTF.getStyleClass().add("TF-EmptyLogin");
            valorFinalTF.getStyleClass().add("TF-EmptyLogin");
            dataTF.getStyleClass().add("TF-EmptyLogin");
            fornecedorCB.getStyleClass().add("TF-EmptyLogin");
            utilizadorTF.getStyleClass().add("TF-EmptyLogin");
            pagamentoCB.getStyleClass().add("TF-EmptyLogin");
            PauseTransition pause = new PauseTransition(Duration.seconds(1.5));
            pause.setOnFinished(e -> {
                Details.setText("");
                Details.getStyleClass().removeAll("invalid-details-error");
                produtoCB.getStyleClass().removeAll("TF-EmptyLogin");
                valorTF.getStyleClass().removeAll("TF-EmptyLogin");
                quantidadeTF.getStyleClass().removeAll("TF-EmptyLogin");
                valorFinalTF.getStyleClass().removeAll("TF-EmptyLogin");
                dataTF.getStyleClass().removeAll("TF-EmptyLogin");
                fornecedorCB.getStyleClass().removeAll("TF-EmptyLogin");
                utilizadorTF.getStyleClass().removeAll("TF-EmptyLogin");
                pagamentoCB.getStyleClass().removeAll("TF-EmptyLogin");
            });
            pause.play();
        }
    }

    public void closeButtonOnAction(ActionEvent event) throws IOException {
        Stage dialogStage = (Stage) closeButton.getScene().getWindow();
        dialogStage.close();
    }

}
