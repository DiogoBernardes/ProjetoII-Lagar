package IPVC.Admin.Product;

import IPVC.BLL.EntidadeBLL;
import IPVC.BLL.ProdutoBLL;
import IPVC.BLL.TipoProdutoBLL;
import IPVC.DAL.Entidade;
import IPVC.DAL.Produto;
import IPVC.DAL.TipoProduto;
import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.List;

public class editProductController {
    @FXML
    private TextField nomeTF;
    @FXML
    private TextField valorTF;
    @FXML
    private TextField quantidadeTF;
    @FXML
    private TextField unidadeTF;
    @FXML
    private ComboBox<String> tipoProdutoCB;
    @FXML
    private TextField imagemTF;
    @FXML
    private TextArea descricaoTF;

    @FXML
    private Button closeButton;
    @FXML
    private Label Details;

    private Produto produto;
    public void setProduct(Produto produto) {
        this.produto = produto;
        nomeTF.setText(produto.getNome());
        valorTF.setText(String.valueOf(produto.getValor_Unitario()));
        quantidadeTF.setText(String.valueOf(produto.getQuantidade()));
        unidadeTF.setText(produto.getUnidade());
        imagemTF.setText(produto.getImagem());
        descricaoTF.setText(produto.getDescricao());
        List<TipoProduto> tiposProduto = TipoProdutoBLL.index();
        ObservableList<String> tiposProdutoDescricoes = FXCollections.observableArrayList();
        for (TipoProduto tipo : tiposProduto) {
            tiposProdutoDescricoes.add(tipo.getDescricao());
        }
        tipoProdutoCB.setItems(tiposProdutoDescricoes);

        String tipoProdutoSelecionado = produto.getTipoProduto().getDescricao();
        if (tiposProdutoDescricoes.contains(tipoProdutoSelecionado)) {
            tipoProdutoCB.setValue(tipoProdutoSelecionado);
        }

    }

    @FXML
    private void updateProduct(ActionEvent event) throws IOException {
        if (produto != null) {
            produto.setNome(nomeTF.getText());
            produto.setValor_Unitario(Double.parseDouble(valorTF.getText()));
            produto.setQuantidade(Integer.parseInt(quantidadeTF.getText()));
            produto.setUnidade(unidadeTF.getText());
            produto.setDescricao(descricaoTF.getText());
            produto.setImagem(imagemTF.getText());
            String tipoProdutoDescricao = tipoProdutoCB.getSelectionModel().getSelectedItem();
            TipoProduto tipoProduto = TipoProdutoBLL.getByDescription(tipoProdutoDescricao);
            produto.setTipoProduto(tipoProduto);
            ProdutoBLL.update(produto);

            Details.getStyleClass().add("valid-details");
            Details.setText("Produto editado com Sucesso!");
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
