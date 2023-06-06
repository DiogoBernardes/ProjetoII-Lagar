package IPVC.Controller;

import IPVC.BLL.ProdutoBLL;
import IPVC.DAL.Produto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


import java.util.List;

@Controller
public class homeController {

    @GetMapping("/home")
    public String home(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        String nomeUtilizador = (String) session.getAttribute("nomeUtilizador");

        List<Produto> produtos = ProdutoBLL.getTypeProduct(5);

        model.addAttribute("nomeUtilizador", nomeUtilizador);
        model.addAttribute("produtos", produtos);

        return "home";
    }
}
