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

public class editSalesController {
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
    private TextField utilizadorTF;
    @FXML
    private TextField valorTF;
    @FXML
    private TextField valorFinalTF;
    @FXML
    private Button closeButton;
    @FXML
    private Label Details;

    private Recibo recibo;
    private LinhaRecibo linhaRecibo;

    public void setSales(LinhaRecibo linhaRecibo, Recibo recibo) {
        this.linhaRecibo = linhaRecibo;
        this.recibo = recibo;
        List<Produto> produtos = ProdutoBLL.index();
        List<Entidade> entidades = EntidadeBLL.getEntities(2);
        List<TipoPagamento> pagamentos = TipoPagamentoBLL.index();
        ObservableList<String> produto = FXCollections.observableArrayList();
        ObservableList<String> clientes = FXCollections.observableArrayList();
        ObservableList<String> pagamento = FXCollections.observableArrayList();
        for (Produto p : produtos) {
            produto.add(p.getNome());
        }
        for (Entidade e : entidades) {
            clientes.add(e.getNome());
        }
        for (TipoPagamento tp : pagamentos) {
            pagamento.add(tp.getDescricao());
        }
        produtoCB.setItems(produto);
        clienteCB.setItems(clientes);;
        pagamentoCB.setItems(pagamento);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        dataTF.setText(sdf.format(linhaRecibo.getRecibo().getData()));
        valorTF.setText(String.valueOf(linhaRecibo.getValor()));
        quantidadeTF.setText(String.valueOf(linhaRecibo.getQuantidade()));
        produtoCB.setValue(linhaRecibo.getProduto().getNome());
        clienteCB.setValue(recibo.getEntidade().getNome());
        pagamentoCB.setValue(recibo.getTipoPagamento().getDescricao());
        double valorFinal = Double.parseDouble(valorTF.getText()) * recibo.getIva();
        valorFinalTF.setText(String.format("%.2f", valorFinal));

        // Adiciona listeners para atualizar o valor final
        quantidadeTF.textProperty().addListener((observable, oldValue, newValue) -> {
            atualizarValorFinal();
        });

        valorTF.textProperty().addListener((observable, oldValue, newValue) -> {
            atualizarValorFinal();
        });

    }
    private void atualizarValorFinal() {
        double valor = Double.parseDouble(valorTF.getText());
        double valorFinal = valor * 1.23;
        valorFinalTF.setText(String.format(Locale.US, "%.2f", valorFinal));
    }

    //Problemas com a linhaFatura, é passada a null..
    @FXML
    private void updateSales(ActionEvent event) throws IOException, ParseException {
       if (recibo != null && linhaRecibo != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            Date data = sdf.parse(dataTF.getText());
            String clienteNome = clienteCB.getSelectionModel().getSelectedItem();
            String tipoPagamentoDesc = pagamentoCB.getSelectionModel().getSelectedItem();
            String produtoNome = produtoCB.getSelectionModel().getSelectedItem();
            Entidade cliente = EntidadeBLL.getEntityByName(clienteNome);
            TipoPagamento pagamento = TipoPagamentoBLL.getPaymentByDescription(tipoPagamentoDesc);
            Produto produto = ProdutoBLL.getName(produtoNome);

            recibo.setEntidade(cliente);
            recibo.setData(data);
            recibo.setValor(Double.parseDouble(valorTF.getText()));
            recibo.setIva(1.23);
            String valorF = valorFinalTF.getText().replace(",", ".");
            recibo.setValor_Final(Double.parseDouble(valorF));
            recibo.setTipoPagamento(pagamento);

            linhaRecibo.setProduto(produto);
            linhaRecibo.setRecibo(recibo);
            linhaRecibo.setQuantidade(Integer.parseInt(quantidadeTF.getText()));
            linhaRecibo.setValor(Double.parseDouble(valorTF.getText()));

            ReciboBLL.update(recibo);
            LinhaReciboBLL.update(linhaRecibo);

            Details.getStyleClass().add("valid-details");
            Details.setText("Recibo editada com Sucesso!");
            PauseTransition pause = new PauseTransition(Duration.seconds(1.5));
            pause.setOnFinished(e -> {
                Details.setText("");
                Details.getStyleClass().removeAll("invalid-details-error");
            });
            pause.play();
       }else{
           Details.getStyleClass().add("valid-details");
           Details.setText("ERRO!");
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
