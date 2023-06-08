package IPVC.Controller;

import IPVC.BLL.EntidadeBLL;
import IPVC.BLL.TipoEntidadeBLL;
import IPVC.DAL.Entidade;
import IPVC.models.ProfileUserFormData;
import IPVC.models.RegisterUserFormData;
import org.apache.catalina.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class userProfileController {

    @GetMapping("/profile")
    public String userProfile(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        String nomeUtilizador = (String) session.getAttribute("nomeUtilizador");

        Entidade currentUser = EntidadeBLL.getEntityByName(nomeUtilizador);

        ProfileUserFormData user = new ProfileUserFormData();
        user.setNome(currentUser.getNome());
        user.setNum_porta(String.valueOf(currentUser.getN_Porta()));
        user.setNIF(String.valueOf(currentUser.getNIF()));
        user.setEmail(currentUser.getEmail());
        user.setTelemovel(String.valueOf(currentUser.getTelemovel()));
        user.setRua(currentUser.getRua());
        user.setCod_postal(currentUser.getCod_Postal());
        user.setUsername(currentUser.getUsername());
        user.setPassword(currentUser.getPassword());
        model.addAttribute("user", user);



        return "userProfile";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.removeAttribute("nomeUtilizador");

        return "redirect:/home";
    }

    @PostMapping(value = "/editProfile")
    public String updateUserInformation(@Valid @ModelAttribute("user") ProfileUserFormData user, BindingResult result, Model model, HttpSession session) {

        if (result.hasErrors()) {
            result.getAllErrors().forEach(System.out::println);
            return "userProfile";
        }

        String nomeUtilizador = (String) session.getAttribute("nomeUtilizador");
        Entidade entidade = EntidadeBLL.getEntityByName(nomeUtilizador);

        if(entidade == null) {
            return "redirect:/login";
        }

        boolean emailExists = !entidade.getEmail().equals(user.getEmail()) && EntidadeBLL.checkEmail(user.getEmail());
        boolean phoneExists = !String.valueOf(entidade.getTelemovel()).equals(user.getTelemovel()) && EntidadeBLL.checkTelemovel(String.valueOf(user.getTelemovel()));
        boolean nifExists = !String.valueOf(entidade.getNIF()).equals(user.getNIF()) && EntidadeBLL.checkNIF(String.valueOf(user.getNIF()));

        if (emailExists) {
            result.rejectValue("email", "error.user", "O email já está em uso!");

        } else if (phoneExists) {
            result.rejectValue("telemovel", "error.user", "O telemóvel já está em uso!");
        } else if (nifExists) {
            result.rejectValue("NIF", "error.user", "O NIF já está em uso!");

        } else {
            Entidade currentUser = EntidadeBLL.getEntityByName(nomeUtilizador);
            currentUser.setNome(user.getNome());
            currentUser.setNIF(Integer.parseInt(user.getNIF()));
            currentUser.setEmail(user.getEmail());
            currentUser.setTelemovel(Integer.parseInt(user.getTelemovel()));
            currentUser.setRua(user.getRua());
            currentUser.setN_Porta(Integer.parseInt(user.getNum_porta()));
            currentUser.setCod_Postal(user.getCod_postal());
            currentUser.setUsername(user.getUsername());

            if(!user.getPassword().isBlank())
                currentUser.setPassword(user.getPassword());


            EntidadeBLL.update(currentUser);

            return "userProfile";

        }

        return "userProfile";
    }
}
