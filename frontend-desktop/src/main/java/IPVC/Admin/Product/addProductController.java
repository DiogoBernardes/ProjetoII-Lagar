package IPVC.Admin.Product;

import IPVC.BLL.EntidadeBLL;
import IPVC.BLL.ProdutoBLL;
import IPVC.BLL.TipoEntidadeBLL;
import IPVC.BLL.TipoProdutoBLL;
import IPVC.DAL.Entidade;
import IPVC.DAL.Produto;
import IPVC.DAL.TipoEntidade;
import IPVC.DAL.TipoProduto;
import javafx.animation.PauseTransition;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.List;

public class addProductController {

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

    @FXML
    private void initialize() {
        List<TipoProduto> tiposProduto = TipoProdutoBLL.index();
        ObservableList<String> tiposProdutoDescricoes = FXCollections.observableArrayList();
        for (TipoProduto tipo : tiposProduto) {
            tiposProdutoDescricoes.add(tipo.getDescricao());
        }
        tipoProdutoCB.setItems(tiposProdutoDescricoes);
    }

    public void addProviderButtonOnAction(ActionEvent event) throws IOException {
        if (!nomeTF.getText().isEmpty() || !valorTF.getText().isEmpty() || !quantidadeTF.getText().isEmpty() ||
                !unidadeTF.getText().isEmpty() || !tipoProdutoCB.getItems().isEmpty() || !imagemTF.getText().isEmpty() || !descricaoTF.getText().isEmpty()) {

            boolean nameNotExist = ProdutoBLL.checkName(nomeTF.getText());

            if (!nameNotExist) {
                Details.getStyleClass().add("invalid-details-error");
                Details.setText("O nome de produto inserido já está a ser utilizado!");
                nomeTF.getStyleClass().add("TF-EmptyLogin");
                PauseTransition pause = new PauseTransition(Duration.seconds(1.5));
                pause.setOnFinished(e -> {
                    Details.setText("");
                    Details.getStyleClass().removeAll("invalid-details-error");
                    nomeTF.getStyleClass().removeAll("TF-EmptyLogin");
                });
                pause.play();

            } else if (nameNotExist) {
                Produto newProduct = new Produto();
                newProduct.setNome(nomeTF.getText());
                newProduct.setValor_Unitario(Double.parseDouble(valorTF.getText()));
                newProduct.setQuantidade(Integer.parseInt(quantidadeTF.getText()));
                newProduct.setUnidade(unidadeTF.getText());
                String tipoProdutoDescricao = tipoProdutoCB.getSelectionModel().getSelectedItem();
                TipoProduto tipoProduto = TipoProdutoBLL.getByDescription(tipoProdutoDescricao);
                newProduct.setTipoProduto(tipoProduto);
                newProduct.setImagem(imagemTF.getText());
                newProduct.setDescricao(descricaoTF.getText());
                ProdutoBLL.create(newProduct);

                Details.getStyleClass().add("valid-details");
                Details.setText("Produto inserido com Sucesso!");
                nomeTF.setText("");
                valorTF.setText("");
                quantidadeTF.setText("");
                unidadeTF.setText("");
                tipoProdutoCB.setValue("");
                imagemTF.setText("");
                descricaoTF.setText("");

                PauseTransition pause = new PauseTransition(Duration.seconds(1.5));
                pause.setOnFinished(e -> {
                    Details.setText("");
                    Details.getStyleClass().removeAll("invalid-details-error");
                });
                pause.play();
            }
        } else {
                Details.getStyleClass().add("invalid-details-error");
                Details.setText("Os dados do produto são necessários!");
                nomeTF.getStyleClass().add("TF-EmptyLogin");
                valorTF.getStyleClass().add("TF-EmptyLogin");
                quantidadeTF.getStyleClass().add("TF-EmptyLogin");
                unidadeTF.getStyleClass().add("TF-EmptyLogin");
                tipoProdutoCB.getStyleClass().add("TF-EmptyLogin");
                imagemTF.getStyleClass().add("TF-EmptyLogin");
                descricaoTF.getStyleClass().add("TF-EmptyLogin");
                PauseTransition pause = new PauseTransition(Duration.seconds(1.5));
                pause.setOnFinished(e -> {
                    Details.setText("");
                    Details.getStyleClass().removeAll("invalid-details-error");
                    nomeTF.getStyleClass().removeAll("TF-EmptyLogin");
                    valorTF.getStyleClass().removeAll("TF-EmptyLogin");
                    quantidadeTF.getStyleClass().removeAll("TF-EmptyLogin");
                    unidadeTF.getStyleClass().removeAll("TF-EmptyLogin");
                    tipoProdutoCB.getStyleClass().removeAll("TF-EmptyLogin");
                });
                pause.play();
            }

    }

    public void closeButtonOnAction(ActionEvent event) throws IOException {
        Stage dialogStage = (Stage) closeButton.getScene().getWindow();
        dialogStage.close();
    }

}
