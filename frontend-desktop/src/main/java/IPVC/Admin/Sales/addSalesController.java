package IPVC.Admin.Sales;

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

public class addSalesController {
    @FXML
    private ComboBox<String> tipoProdutoCB;
    @FXML
    private ComboBox<String> produtoCB;
    @FXML
    private TextField quantidadeTF;
    @FXML
    private ComboBox<String> clienteCB;
    @FXML
    private ComboBox<String> pagamentoCB;
    @FXML
    private TextField dataTF;
    @FXML
    private TextField valorUnitarioTF;
    @FXML
    private TextField valorTF;
    @FXML
    private TextField valorFinalTF;
    @FXML
    private Button closeButton;
    @FXML
    private Label Details;


    @FXML
    private void initialize() throws IOException{

        List<Entidade> entidades = EntidadeBLL.getEntities(2);
        List<TipoPagamento> pagamentos = TipoPagamentoBLL.index();
        List<TipoProduto> tiposProduto = TipoProdutoBLL.index();


        ObservableList<String> cliente = FXCollections.observableArrayList();
        ObservableList<String> pagamento = FXCollections.observableArrayList();
        ObservableList<String> tiposProdutoDescricoes = FXCollections.observableArrayList();


        for (Entidade e : entidades) {
            cliente.add(e.getNome());
        }
        for (TipoPagamento tp : pagamentos) {
            pagamento.add(tp.getDescricao());
        }
        for (TipoProduto tipo : tiposProduto) {
            tiposProdutoDescricoes.add(tipo.getDescricao());
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        Date dataAtual = new Date();


        clienteCB.setItems(cliente);;
        pagamentoCB.setItems(pagamento);
        tipoProdutoCB.setItems(tiposProdutoDescricoes);
        String dataFormatada = sdf.format(dataAtual);
        dataTF.setText(dataFormatada);
        quantidadeTF.setText("0");

        // Adiciona listeners para atualizar o valor final
        quantidadeTF.textProperty().addListener((observable, oldValue, newValue) -> {
            atualizarValor();
            atualizarValorFinal();
        });

        valorTF.textProperty().addListener((observable, oldValue, newValue) -> {
            atualizarValorFinal();
        });

        produtoCB.valueProperty().addListener((observable, oldValue, newValue) -> {
            atualizarPrecoUnitario();
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
    private void atualizarPrecoUnitario(){
        String produtoName = produtoCB.getSelectionModel().getSelectedItem();
        Produto prod  = ProdutoBLL.getName(produtoName);
        valorUnitarioTF.setText(String.valueOf(prod.getValor_Unitario()));
    }
    private void atualizarValor(){
        String produtoName = produtoCB.getSelectionModel().getSelectedItem();
        Produto produto  = ProdutoBLL.getName(produtoName);
        Double valor = produto.getValor_Unitario() * Double.parseDouble(quantidadeTF.getText());

        valorTF.setText(String.format(Locale.US, "%.2f", valor));
    }
    private void atualizarValorFinal() {
        String produtoName = produtoCB.getSelectionModel().getSelectedItem();
        Produto prod  = ProdutoBLL.getName(produtoName);
        double valor = Double.parseDouble(valorTF.getText());
        double valorFinal = valor * 1.23;
        valorFinalTF.setText(String.format(Locale.US, "%.2f", valorFinal));
    }


    public void addSalesButtonOnAction(ActionEvent event) throws IOException, ParseException {

        String prodNome= produtoCB.getSelectionModel().getSelectedItem();
        Produto prod = ProdutoBLL.getName(prodNome);
        int prodQuantidade = prod.getQuantidade();
        int qtdInserida = Integer.parseInt(quantidadeTF.getText());

        if (produtoCB.getValue() == null || produtoCB.getValue() == null || clienteCB.getValue() == null ||
                quantidadeTF.getText().isEmpty() || pagamentoCB.getValue() == null){
            Details.getStyleClass().add("invalid-details-error");
            Details.setText("Os dados do recibo são necessários!");
            produtoCB.getStyleClass().add("TF-EmptyLogin");
            valorTF.getStyleClass().add("TF-EmptyLogin");
            quantidadeTF.getStyleClass().add("TF-EmptyLogin");
            valorFinalTF.getStyleClass().add("TF-EmptyLogin");
            valorUnitarioTF.getStyleClass().add("TF-EmptyLogin");
            tipoProdutoCB.getStyleClass().add("TF-EmptyLogin");
            dataTF.getStyleClass().add("TF-EmptyLogin");
            clienteCB.getStyleClass().add("TF-EmptyLogin");
            pagamentoCB.getStyleClass().add("TF-EmptyLogin");
            PauseTransition pause = new PauseTransition(Duration.seconds(1.5));
            pause.setOnFinished(e -> {
                Details.setText("");
                Details.getStyleClass().removeAll("invalid-details-error");
                produtoCB.getStyleClass().removeAll("TF-EmptyLogin");
                valorTF.getStyleClass().removeAll("TF-EmptyLogin");
                quantidadeTF.getStyleClass().removeAll("TF-EmptyLogin");
                valorFinalTF.getStyleClass().removeAll("TF-EmptyLogin");
                valorUnitarioTF.getStyleClass().removeAll("TF-EmptyLogin");
                tipoProdutoCB.getStyleClass().removeAll("TF-EmptyLogin");
                dataTF.getStyleClass().removeAll("TF-EmptyLogin");
                clienteCB.getStyleClass().removeAll("TF-EmptyLogin");
                pagamentoCB.getStyleClass().removeAll("TF-EmptyLogin");
            });
            pause.play();

        }


        if(qtdInserida <= prodQuantidade) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            Date data = sdf.parse(dataTF.getText());
            String clienteNome = clienteCB.getSelectionModel().getSelectedItem();
            String pagamentoDescricao = pagamentoCB.getSelectionModel().getSelectedItem();
            String produtoNome = produtoCB.getSelectionModel().getSelectedItem();
            Entidade cliente = EntidadeBLL.getEntityByName(clienteNome);
            TipoPagamento pagamento = TipoPagamentoBLL.getPaymentByDescription(pagamentoDescricao);
            Produto produto = ProdutoBLL.getName(produtoNome);

            Recibo newRecibo = new Recibo();
            LinhaRecibo newLinhaRecibo = new LinhaRecibo();

            newRecibo.setEntidade(cliente);
            newRecibo.setData(data);
            newRecibo.setValor(Double.parseDouble(valorTF.getText()));
            newRecibo.setIva(1.23);
            newRecibo.setValor_Final(Double.parseDouble(valorFinalTF.getText()));
            newRecibo.setTipoPagamento(pagamento);

            newLinhaRecibo.setProduto(produto);
            newLinhaRecibo.setQuantidade(Integer.parseInt(quantidadeTF.getText()));
            newLinhaRecibo.setValor(Double.parseDouble(valorTF.getText()));
            newLinhaRecibo.setRecibo(newRecibo);

            ReciboBLL.create(newRecibo);
            LinhaReciboBLL.create(newLinhaRecibo);

            Details.getStyleClass().add("valid-details");
            Details.setText("Recibo inserida com Sucesso!");
            produtoCB.setValue("");
            valorTF.setText("");
            valorFinalTF.setText("");
            quantidadeTF.setText("");
            dataTF.setText("");
            clienteCB.setValue("");
            pagamentoCB.setValue("");

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
