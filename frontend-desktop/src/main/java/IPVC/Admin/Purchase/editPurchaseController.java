package IPVC.Admin.Purchase;

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

public class editPurchaseController {
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

    private Fatura fatura;
    private LinhaFatura linhaFatura;

    public void setPurchase(LinhaFatura linhaFatura, Fatura fatura) {
        this.linhaFatura = linhaFatura;
        this.fatura = fatura;

        List<Entidade> entidades = EntidadeBLL.getEntities(1);
        List<TipoPagamento> pagamentos = TipoPagamentoBLL.index();
        List<TipoProduto> tiposProduto = TipoProdutoBLL.index();

        ObservableList<String> fornecedores = FXCollections.observableArrayList();
        ObservableList<String> pagamento = FXCollections.observableArrayList();
        ObservableList<String> tiposProdutoDescricoes = FXCollections.observableArrayList();

        for (Entidade e : entidades) {
            fornecedores.add(e.getNome());
        }
        for (TipoPagamento tp : pagamentos) {
            pagamento.add(tp.getDescricao());
        }
        for (TipoProduto tipo : tiposProduto) {
            tiposProdutoDescricoes.add(tipo.getDescricao());
        }

        fornecedorCB.setItems(fornecedores);;
        pagamentoCB.setItems(pagamento);
        tipoProdutoCB.setItems(tiposProdutoDescricoes);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        dataTF.setText(sdf.format(linhaFatura.getFatura().getData()));
        utilizadorTF.setText(currentUser.getNome());
        valorTF.setText(String.valueOf(linhaFatura.getValor()));
        quantidadeTF.setText(String.valueOf(linhaFatura.getQuantidade()));
        produtoCB.setValue(linhaFatura.getProduto().getNome());
        fornecedorCB.setValue(fatura.getEntidade().getNome());
        pagamentoCB.setValue(fatura.getTipoPagamento().getDescricao());



        // Adiciona listeners para atualizar o valor final
        quantidadeTF.textProperty().addListener((observable, oldValue, newValue) -> {
            atualizarValor();
            atualizarValorFinal();
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
        String produtoName = produtoCB.getSelectionModel().getSelectedItem();
        Produto produto  = ProdutoBLL.getName(produtoName);
        Double valor = produto.getValor_Unitario() * Double.parseDouble(quantidadeTF.getText());

        valorTF.setText(String.format(Locale.US, "%.2f", valor));
    }
    private void atualizarValorFinal() {
        double valor = Double.parseDouble(valorTF.getText());
        double valorFinal = valor * 1.23;
        valorFinalTF.setText(String.format(Locale.US, "%.2f", valorFinal));
    }


    @FXML
    private void updatePurchase(ActionEvent event) throws IOException, ParseException {
       if (fatura == null || linhaFatura == null) {
           Details.getStyleClass().add("valid-details");
           Details.setText("ERRO!");
           PauseTransition pause = new PauseTransition(Duration.seconds(1.5));
           pause.setOnFinished(e -> {
               Details.setText("");
               Details.getStyleClass().removeAll("invalid-details-error");
           });
           pause.play();

       }else{
           SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
           Date data = sdf.parse(dataTF.getText());
           String fornecedorNome = fornecedorCB.getSelectionModel().getSelectedItem();
           String tipoPagamentoDesc = pagamentoCB.getSelectionModel().getSelectedItem();
           String produtoNome = produtoCB.getSelectionModel().getSelectedItem();
           Entidade fornecedor = EntidadeBLL.getEntityByName(fornecedorNome);
           TipoPagamento pagamento = TipoPagamentoBLL.getPaymentByDescription(tipoPagamentoDesc);
           Produto produto = ProdutoBLL.getName(produtoNome);

           fatura.setEntidade(fornecedor);
           fatura.setUtilizador(currentUser);
           fatura.setData(data);
           fatura.setValor(Double.parseDouble(valorTF.getText()));
           fatura.setIva(1.23);
           String valorF = valorFinalTF.getText().replace(",", ".");
           fatura.setValor_Total(Double.parseDouble(valorF));
           fatura.setTipoPagamento(pagamento);

           linhaFatura.setProduto(produto);
           linhaFatura.setFatura(fatura);
           linhaFatura.setQuantidade(Integer.parseInt(quantidadeTF.getText()));
           linhaFatura.setValor(Double.parseDouble(valorTF.getText()));

           FaturaBLL.update(fatura);
           LinhaFaturaBLL.update(linhaFatura);

           Details.getStyleClass().add("valid-details");
           Details.setText("Fatura editada com Sucesso!");
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
