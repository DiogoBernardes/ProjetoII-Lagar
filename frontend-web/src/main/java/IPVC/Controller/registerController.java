package IPVC.Controller;

import IPVC.BLL.EntidadeBLL;
import IPVC.BLL.TipoEntidadeBLL;
import IPVC.DAL.Entidade;
import IPVC.models.RegisterUserFormData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;


@Controller
public class registerController {

    @GetMapping("/register")
    public String home(Model model) {
        model.addAttribute("user",new RegisterUserFormData());
        return "register";
    }

    @PostMapping(value = "/register")
    public String registerSubmit(@Valid @ModelAttribute("user") RegisterUserFormData user, BindingResult result, Model model) {

        if (result.hasErrors()) {
            return "register";
        }

        if (!user.getPassword().equals(user.getConfirmPassword())) {
            result.rejectValue("confirmPassword", "error.user", "As passowrds não coincidem");
            return "register";
        }


            boolean emailNotExist = EntidadeBLL.checkEmail(user.getEmail());
            boolean phoneNotExist = EntidadeBLL.checkTelemovel(user.getTelemovel());
            boolean nifNotExist = EntidadeBLL.checkNIF(user.getNIF());

            if (!emailNotExist) {
                result.rejectValue("email", "error.user", "O email já está em uso!");
            } else if (!phoneNotExist) {
                result.rejectValue("telemovel", "error.user", "O telemóvel já está em uso!");
            } else if (!nifNotExist) {
                result.rejectValue("NIF", "error.user", "O NIF já está em uso!");
            } else if (emailNotExist && phoneNotExist && nifNotExist) {
                Entidade newUser = new Entidade();


                newUser.setNome(user.getNome());
                newUser.setNIF(Integer.parseInt(user.getNIF()));
                newUser.setEmail(user.getEmail());
                newUser.setTelemovel(Integer.parseInt(user.getTelemovel()));
                newUser.setRua(user.getRua());
                newUser.setN_Porta(Integer.parseInt(user.getNum_porta()));
                newUser.setCod_Postal(user.getCod_postal());
                newUser.setTipoEntidade(TipoEntidadeBLL.get(2));
                newUser.setUsername(user.getUsername());
                newUser.setPassword(user.getPassword());

                EntidadeBLL.update(newUser);

                return "redirect:/login";

            }

        return "register";
    }
}
