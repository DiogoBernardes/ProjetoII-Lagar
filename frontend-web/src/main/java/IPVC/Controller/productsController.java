package IPVC.Controller;

import IPVC.BLL.ProdutoBLL;
import IPVC.DAL.Produto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

@Controller
public class productsController {

    @GetMapping("/products")
    public String products(Model model) {
        List<Produto> produtos = ProdutoBLL.getTypeProduct(5);

        model.addAttribute("produtos", produtos);

        return "products";
    }
}
