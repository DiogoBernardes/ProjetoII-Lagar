package IPVC.Controller;

import IPVC.BLL.EntidadeBLL;
import IPVC.DAL.Entidade;
import IPVC.utils.Session;
import javax.servlet.http.HttpSession;
import IPVC.models.LoginUserFormData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;


@Controller
public class loginController {


    @GetMapping("/login")
    public String login(Model model){
        model.addAttribute("user", new LoginUserFormData());
        return "login";
    }

    @PostMapping(value = "/login")
    public String loginSubmit(@Valid @ModelAttribute("user") LoginUserFormData user, BindingResult result, Model model, HttpSession session) {
        if (result.hasErrors()) {
            return "login";
        }

        if (EntidadeBLL.checkLogin(user.getUsername(), user.getPassword())) {
            Entidade currentUser = EntidadeBLL.getDataLogin(user.getUsername(), user.getPassword());
            session.setAttribute("nomeUtilizador", currentUser.getNome());


            return "redirect:/home";
        } else {
            result.rejectValue("password", "error.user", "Wrong email or password.");

            return "login";
        }
    }


}
