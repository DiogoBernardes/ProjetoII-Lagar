package IPVC.Controller;

import IPVC.BLL.EntidadeBLL;
import IPVC.DAL.Entidade;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class userProfileController {


    @GetMapping("/profile")
    public String userProfile(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        String nomeUtilizador = (String) session.getAttribute("nomeUtilizador");


        Entidade currentUser = EntidadeBLL.getEntityByName(nomeUtilizador);
        model.addAttribute("user", currentUser);
        model.addAttribute("nome", currentUser.getNome());
        model.addAttribute("email", currentUser.getEmail());
        model.addAttribute("NIF",currentUser.getNIF());
        model.addAttribute("telemovel", currentUser.getTelemovel());
        model.addAttribute("rua", currentUser.getRua());
        model.addAttribute("N_Porta",currentUser.getN_Porta());
        model.addAttribute("Cod_Postal",currentUser.getCod_Postal());
        model.addAttribute("rua", currentUser.getRua());
        model.addAttribute("cod_postal",currentUser.getCod_Postal());
        model.addAttribute("username", currentUser.getUsername());
        model.addAttribute("password", currentUser.getPassword());
        model.addAttribute("confirmPassword", currentUser.getPassword());



        return "userProfile";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.removeAttribute("nomeUtilizador");


        return "redirect:/home";
    }
}
