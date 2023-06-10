package IPVC.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class aboutUsController {

    @GetMapping("/aboutUs")
    public String home(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        String nomeUtilizador = (String) session.getAttribute("nomeUtilizador");
        model.addAttribute("nomeUtilizador", nomeUtilizador);

        return "aboutUs";
    }
}
