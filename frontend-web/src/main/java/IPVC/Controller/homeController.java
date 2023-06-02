package IPVC.Controller;

import IPVC.BLL.ProdutoBLL;
import IPVC.DAL.Produto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


import java.util.List;

@Controller
public class homeController {

    @GetMapping("/home")
    public String home(Model model) {
        List<Produto> produtos = ProdutoBLL.getTypeProduct(5);

        model.addAttribute("produtos", produtos);
        return "home";
    }
}
